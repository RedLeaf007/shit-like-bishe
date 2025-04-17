package com.design.appproject.ui.exampaper

import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.design.appproject.base.BaseBindingFragment
import com.design.appproject.base.CommonArouteApi
import com.design.appproject.databinding.ActivityExampaperListLayoutBinding
import com.design.appproject.logic.viewmodel.exampaper.ExamPaperModel
import com.design.appproject.utils.ArouterUtils

/**
 * 试卷列表界面
 */
@Route(path = CommonArouteApi.PATH_FRAGMENT_LIST_EXAMPAPER)
class ListFragment: BaseBindingFragment<ActivityExampaperListLayoutBinding>() {

    @JvmField
    @Autowired
    var mHasBack: Boolean = true /*是否有返回按钮*/

    private val mExamPaperModel by viewModels<ExamPaperModel>()

    private val mParams = mutableMapOf(
        "page" to "1",
        "status" to "1",
        "limit" to "20"
    )

    override fun initEvent() {
        binding.apply {
            setBarTitle("问卷",mHasBack)
            srv.setOnRefreshListener {
                mParams["page"] ="1"
                mExamPaperModel.exampaperList(mParams)
            }
            srv.setAdapter(ListAdapter().apply {
                pageLoadMoreListener {
                    mExamPaperModel.exampaperList(mParams.apply {
                        mParams["page"] = it.toString()
                    })
                }
                setOnItemClickListener { adapter, view, position ->
                    ArouterUtils.startFragment(CommonArouteApi.PATH_FRAGMENT_DETAILS_EXAMPAPER,map = mapOf("mExamPaper" to data[position]) )
                }
            })

            searchBtn.setOnClickListener {
               val searchValue = searchValueEt.text.toString()
                mParams["page"] ="1"
                if (searchValue.isNullOrEmpty()){
                    mParams.remove("name")
                }else{
                    mParams["name"] = "%$searchValue%"
                }
                mExamPaperModel.exampaperList(mParams)
            }
        }
    }

    override fun initData() {
        super.initData()
        mExamPaperModel.exampaperList(mParams)
        mExamPaperModel.exampaperListLiveData.observeKt(errorBlock = {
            binding.srv.isRefreshing =false
        }) {
            it.getOrNull()?.let {
                binding.srv.setData(it.data.list,it.data.total)
            }
        }
    }
}