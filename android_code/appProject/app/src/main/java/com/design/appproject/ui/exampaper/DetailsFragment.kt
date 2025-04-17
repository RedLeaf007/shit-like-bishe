package com.design.appproject.ui.exampaper

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.children
import androidx.core.view.setPadding
import androidx.core.view.isVisible
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.GsonUtils
import com.design.appproject.R
import com.design.appproject.base.BaseBindingFragment
import com.design.appproject.base.CommonArouteApi
import com.design.appproject.base.CommonBean
import com.design.appproject.bean.exampaper.ExampaperItemBean
import com.design.appproject.bean.exampaper.ExamquestionItemBean
import com.design.appproject.bean.exampaper.Options
import com.design.appproject.bean.examrecord.ExamrecordItemBean
import com.design.appproject.databinding.ActivityExampaperDetailsLayoutBinding
import com.design.appproject.logic.repository.HomeRepository
import com.design.appproject.logic.repository.UserRepository
import com.design.appproject.utils.Utils
import com.design.appproject.widget.MyEditTextView
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import com.lxj.xpopup.XPopup
import com.union.union_basic.ext.*
import com.union.union_basic.utils.StorageUtil
import java.util.*
import kotlin.concurrent.timerTask

/**
 * 试卷详情界面
 */
@Route(path = CommonArouteApi.PATH_FRAGMENT_DETAILS_EXAMPAPER)
class DetailsFragment: BaseBindingFragment<ActivityExampaperDetailsLayoutBinding>() {

    @JvmField
    @Autowired
    var mExamPaper: ExampaperItemBean =ExampaperItemBean() /*试卷数据*/

    var currentItem = 0/*当前选择的问题index*/

    var mScore = 0L/*答题总分*/

    var questionList:List<ExamquestionItemBean> = listOf()/*问题列表*/

    var answerList:MutableList<String> = mutableListOf()

    private var mRemainingTime =0L

    override fun initEvent() {
        binding.apply {
            mRemainingTime= mExamPaper.time *60000L
            mTimer.schedule(remainingTimerTask, 0, 1000)
            submitTv.text = "退出考试"
            submitTv.setOnClickListener {
                XPopup.Builder(requireActivity())
                    .asConfirm("提示","确定退出考试") {
                        requireActivity().finish()
                    }.show()
            }
        }
    }

    private fun examFinish(){
        submit()
    }


    /*获取作答信息*/
    private fun getAnswer(questionItemBean: ExamquestionItemBean,questionLL:LinearLayoutCompat) = if (questionItemBean.type == 3L ||questionItemBean.type ==4L){/*填空题*/
        questionLL.findViewById<LinearLayoutCompat>(R.id.question_llc).children.last().toConversion<EditText>()?.text.toString()
    }else{
        questionLL.findViewById<LinearLayoutCompat>(R.id.question_llc).children.filter { it.toConversion<CheckBox>()?.isChecked?:false }
            .map { it.tag.toString().trim() }.joinToString(",")
    }

    /*初始化问题布局*/
    private fun ActivityExampaperDetailsLayoutBinding.initQuestionView(questionItemBean: ExamquestionItemBean,questionLL:LinearLayoutCompat){
        when(questionItemBean.type){
            0L->{
                val options = GsonUtils.fromJson<List<Options>>(questionItemBean.options,object : TypeToken<List<Options>>() {}.type)
                questionLL.findViewById<TextView>(R.id.title_tv).text = "单选题 "+questionItemBean.questionname
                creatCheckBoxList(options,questionLL)
            }
            1L->{
                val options = GsonUtils.fromJson<List<Options>>(questionItemBean.options,object : TypeToken<List<Options>>() {}.type)
                questionLL.findViewById<TextView>(R.id.title_tv).text = "多选题 "+questionItemBean.questionname
                creatCheckBoxList(options,questionLL,true)
            }
            2L->{
                val options = GsonUtils.fromJson<List<Options>>(questionItemBean.options,object : TypeToken<List<Options>>() {}.type)
                questionLL.findViewById<TextView>(R.id.title_tv).text = "判断题 "+questionItemBean.questionname
                creatCheckBoxList(options,questionLL)
            }
            3L->{
                questionLL.findViewById<TextView>(R.id.title_tv).text = "填空题 "+questionItemBean.questionname
                creatInputTextView(questionLL)
            }
            4L->{
                questionLL.findViewById<TextView>(R.id.title_tv).text = "主观题 "+questionItemBean.questionname
                creatInputTextView(questionLL)
            }
        }
    }

    private fun ActivityExampaperDetailsLayoutBinding.creatInputTextView(questionLL:LinearLayoutCompat){
        val questionView = questionLL.findViewById<LinearLayoutCompat>(R.id.question_llc)
        questionView.orientation = LinearLayoutCompat.HORIZONTAL
        questionView.addView(TextView(requireActivity()).apply {
            text ="填写答案:"
        })
        questionView.addView(MyEditTextView(requireActivity()).apply {
            setBackgroundResource(R.drawable.shape_radius10_stroke_gray)
            gravity = Gravity.CENTER_VERTICAL
            setPadding(5.dp,0,0,5.dp)
        },LinearLayout.LayoutParams.MATCH_PARENT,40.dp)
    }
    private fun ActivityExampaperDetailsLayoutBinding.creatCheckBoxList(options:List<Options>,questionLL:LinearLayoutCompat,isMultiple:Boolean=false){
        val questionView = questionLL.findViewById<LinearLayoutCompat>(R.id.question_llc)
        questionView.orientation = LinearLayoutCompat.VERTICAL
        options.forEach {option->
            questionView.addView(CheckBox(requireActivity()).apply {
                text = option.text
                tag = option.code
                setOnClickListener {
                    isMultiple.no {
                        questionView.children.filter { it!=this }.forEach {view->
                            view.toConversion<CheckBox>()?.isChecked =false
                        }
                    }
                }
            })
        }
    }


    lateinit var mUserBean: LinkedTreeMap<String, Any>/*当前用户数据*/

    override fun initData() {
        super.initData()
        HomeRepository.list<ExamquestionItemBean>("examquestion", mapOf(
            "page" to "1","limit" to "999","sort" to "sequence","paperid" to mExamPaper.id.toString()
        )).observeKt {
            it.getOrNull()?.let {
                questionList = it.data.list
                binding.countTv.text = "题目：1/${questionList.size}"
                deleteRecord()
                binding.countTv.text = "题目总共${questionList.size}道"
                binding.ll.removeAllViews()
                questionList.forEach {
                    val questionLL = LayoutInflater.from(requireActivity()).inflate(R.layout.item_exampaper_details_layout,null,true).toConversion<LinearLayoutCompat>()
                    binding.ll.addView(questionLL)
                    questionLL?.marginKTX(10.dp,10.dp,10.dp,10.dp)
                    binding.initQuestionView(it,questionLL!!)
                }
                binding.ll.addView(Button(requireActivity()).apply {
                    text = "立即交卷"
                    setOnClickListener {
                        submit()
                    }
                })
            }
        }
        UserRepository.session<Any>().observeKt {
            it.getOrNull()?.let {
                it.data.toConversion<LinkedTreeMap<String, Any>>()?.let {
                    mUserBean = it
                }
            }
        }
    }

    /*提交答案*/
    private fun submit(){
        binding.ll.children.filter { it is LinearLayoutCompat}.forEachIndexed { index, questionView ->
            questionView.toConversion<LinearLayoutCompat>()?.let { it1 ->
                val answer = getAnswer(questionList[index], it1)
                val isCorrect = answer==questionList[index].answer
                answerList.add(answer+" "+(if(isCorrect)"正确" else "错误"))
                mScore += if (isCorrect) questionList[index].score else 0L
                saveRecord(questionList[index],if (isCorrect) questionList[index].score else 0L,answer, type = questionList[index].type)
            }
        }
        binding.ll.removeAllViews()
        binding.ll.addView(TextView(requireActivity()).apply {
            setPadding(10.dp)
            gravity = Gravity.CENTER_HORIZONTAL
            text = "考试成绩："+mScore
        })
        val buttonLL = LinearLayout(requireActivity()).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_HORIZONTAL
        }
        binding.ll.addView(buttonLL)
        buttonLL.addView(Button(requireActivity()).apply {
            text = "查看解析"
            setOnClickListener {
                buttonLL.removeView(this)
                questionList.forEachIndexed { index, examquestionItemBean ->
                    val questionLL = LayoutInflater.from(requireActivity()).inflate(R.layout.item_exampaper_details_layout,null,true).toConversion<LinearLayoutCompat>()
                    binding.ll.addView(questionLL)
                    questionLL?.marginKTX(10.dp,10.dp,10.dp,10.dp)
                    initAnalysisView(examquestionItemBean,answerList[index],questionLL!!)
                }
            }
        })
        buttonLL.addView(Button(requireActivity()).apply {
            text = "结束考试"
            setOnClickListener {
                requireActivity().finish()
            }
        })
    }

    /*初始化解析类型*/
    private fun initAnalysisView(question:ExamquestionItemBean,answer:String,questionLL:LinearLayoutCompat){
        val textview = TextView(requireActivity())
        textview.text = when(question.type){
            1L->"多选题:"+question.questionname
            2L-> "判断题:"+question.questionname
            3L->"填空题:"+question.questionname
            4L->"主观题:"+question.questionname
            else->"单选题:"+question.questionname
        }
        questionLL.addView(textview)
        questionLL.addView(TextView(requireActivity()).apply {
            val content = "我的答案:"+answer
            text = content.foregroundColorSpan(content.length-2..content.length,Color.RED)
        })
        questionLL.addView(TextView(requireActivity()).apply {
            text = "查看解析:"
        })
        questionLL.addView(TextView(requireActivity()).apply {
            text = question.analysis
        })
    }

    /*删除答题记录*/
    private fun deleteRecord(){
        HomeRepository.deleteRecords(Utils.getUserId(),mExamPaper.id).observeForever {  }
    }

    /**保存答题记录*/
    private fun saveRecord(item: ExamquestionItemBean,score:Long,answer:String,type:Long){
        HomeRepository.add<Any>("examrecord",ExamrecordItemBean(
            userid = Utils.getUserId(),
            paperid = mExamPaper.id,
            type = type,
            papername = mExamPaper.name,
            questionid=item.id,
            questionname=item.questionname,
            options=item.options,
            score=item.score,
            answer=item.answer,
            analysis=item.analysis,
            myscore=score,
            myanswer=answer
        )).observeKt {
        }
    }


    private val mTimer by lazy {
        Timer()
    }
    private val remainingTimerTask by lazy {
        timerTask {
            requireActivity().runOnUiThread {
               binding.countdownTv.text = "倒计时:${ConvertUtils.millis2FitTimeSpan(mRemainingTime,4)}"
            }
            mRemainingTime -=1000
            if (mRemainingTime <= 0) {
                cancel()
                requireActivity().runOnUiThread {/*答题时间结束*/
                    examFinish()
                }
            }
        }
    }

    override fun onDestroyView() {
        mTimer.cancel()
        remainingTimerTask.cancel()
        super.onDestroyView()
    }
}