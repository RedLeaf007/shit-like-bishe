package com.design.appproject.ui.yundongjilu

import android.Manifest
import com.union.union_basic.permission.PermissionUtil
import com.design.appproject.ext.UrlPrefix
import androidx.core.widget.addTextChangedListener
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.core.view.children
import com.design.appproject.utils.Utils
import com.design.appproject.bean.BaiKeBean
import androidx.core.app.ActivityCompat.startActivityForResult
import com.blankj.utilcode.util.UriUtils
import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.internal.LinkedTreeMap
import com.union.union_basic.ext.*
import com.blankj.utilcode.util.RegexUtils
import com.union.union_basic.utils.StorageUtil
import com.github.gzuliyujiang.wheelpicker.DatimePicker
import com.design.appproject.widget.BottomSpinner
import com.design.appproject.base.CommonBean
import com.blankj.utilcode.util.TimeUtils
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.SimpleCallback
import com.lxj.xpopup.XPopup
import com.github.gzuliyujiang.wheelpicker.DatePicker
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity
import com.github.gzuliyujiang.wheelpicker.entity.DatimeEntity
import com.github.gzuliyujiang.wheelpicker.impl.BirthdayFormatter
import com.github.gzuliyujiang.wheelpicker.impl.UnitTimeFormatter
import java.text.SimpleDateFormat
import android.widget.RatingBar
import com.design.appproject.logic.repository.HomeRepository
import com.design.appproject.logic.repository.UserRepository
import com.union.union_basic.image.selector.SmartPictureSelector
import java.io.File
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.design.appproject.base.BaseBindingActivity
import com.design.appproject.base.CommonArouteApi
import com.design.appproject.bean.YundongjiluItemBean
import com.blankj.utilcode.constant.TimeConstants
import com.design.appproject.ext.afterTextChanged
import com.design.appproject.R
import android.os.Bundle
import android.view.View
import com.design.appproject.bean.JiankangmubiaoItemBean
import com.design.appproject.databinding.YundongjiluaddorupdateLayoutBinding
import com.design.appproject.ext.load
import android.text.InputType

/**
 * 运动记录新增或修改类
 */
@Route(path = CommonArouteApi.PATH_ACTIVITY_ADDORUPDATE_YUNDONGJILU)
class AddOrUpdateActivity:BaseBindingActivity<YundongjiluaddorupdateLayoutBinding>() {

    @JvmField
    @Autowired
    var mId: Long = 0L /*id*/

    @JvmField
    @Autowired
    var mCrossTable: String = "" /*跨表表名*/

    @JvmField
    @Autowired
    var mCrossObj: JiankangmubiaoItemBean = JiankangmubiaoItemBean() /*跨表表内容*/

    @JvmField
    @Autowired
    var mStatusColumnName: String = "" /*列名*/

    @JvmField
    @Autowired
    var mStatusColumnValue: String = "" /*列值*/

    @JvmField
    @Autowired
    var mTips: String = "" /*提示*/
    @JvmField
    @Autowired
    var mRefid: Long = 0 /*refid数据*/

    /**上传数据*/
    var mYundongjiluItemBean = YundongjiluItemBean()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置透明主题
        if (mStatusColumnName!="&jiangpinmingcheng&") {
            setTheme(R.style.Theme_AppProject)
        }else{
            binding.root.visibility= View.GONE
            activityTitleBar.visibility= View.GONE
            //去除窗口动画
            window.setWindowAnimations(0)
        }
    }
    override fun initEvent() {
        setBarTitle("运动记录")
        setBarColor("#FFFFFF","black")
        if (mRefid>0){/*如果上一级页面传递了refid，获取改refid数据信息*/
            if (mYundongjiluItemBean.javaClass.declaredFields.any{it.name == "refid"}){
                mYundongjiluItemBean.javaClass.getDeclaredField("refid").also { it.isAccessible=true }.let {
                    it.set(mYundongjiluItemBean,mRefid)
                }
            }
            if (mYundongjiluItemBean.javaClass.declaredFields.any{it.name == "nickname"}){
                mYundongjiluItemBean.javaClass.getDeclaredField("nickname").also { it.isAccessible=true }.let {
                    it.set(mYundongjiluItemBean,StorageUtil.decodeString(CommonBean.USERNAME_KEY)?:"")
                }
            }
        }
        if (Utils.isLogin() && mYundongjiluItemBean.javaClass.declaredFields.any{it.name == "userid"}){/*如果有登陆，获取登陆后保存的userid*/
            mYundongjiluItemBean.javaClass.getDeclaredField("userid").also { it.isAccessible=true }.let {
                it.set(mYundongjiluItemBean,Utils.getUserId())
            }
        }
        binding.initView()

    }

    fun YundongjiluaddorupdateLayoutBinding.initView(){
            yundongleixingBs.setOptions("有氧,无氧,拉伸".split(","),"请选择运动类型",
            listener = object : BottomSpinner.OnItemSelectedListener {
                override fun onItemSelected(position: Int, content: String) {
                    super.onItemSelected(position, content)
                    yundongleixingBs.text = content
                    mYundongjiluItemBean.yundongleixing =content
                }
            })
            mYundongjiluItemBean.kaishishijian = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            kaishishijianTv.text = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            val mkaishishijianPicker = DatimePicker(this@AddOrUpdateActivity).apply {
            wheelLayout.setDateFormatter(BirthdayFormatter())
            wheelLayout.setTimeFormatter(UnitTimeFormatter())
            wheelLayout.setRange(DatimeEntity.yearOnFuture(-100), DatimeEntity.yearOnFuture(50), DatimeEntity.now())
            setOnDatimePickedListener { year, month, day, hour, minute, second ->
                kaishishijianTv.text = "$year-$month-$day $hour:$minute:$second"
                mYundongjiluItemBean.kaishishijian="$year-$month-$day $hour:$minute:$second"
            }
        }
            kaishishijianTv.setOnClickListener {
            mkaishishijianPicker.show()
        }
            mYundongjiluItemBean.jieshushijian = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            jieshushijianTv.text = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            val mjieshushijianPicker = DatimePicker(this@AddOrUpdateActivity).apply {
            wheelLayout.setDateFormatter(BirthdayFormatter())
            wheelLayout.setTimeFormatter(UnitTimeFormatter())
            wheelLayout.setRange(DatimeEntity.yearOnFuture(-100), DatimeEntity.yearOnFuture(50), DatimeEntity.now())
            setOnDatimePickedListener { year, month, day, hour, minute, second ->
                jieshushijianTv.text = "$year-$month-$day $hour:$minute:$second"
                mYundongjiluItemBean.jieshushijian="$year-$month-$day $hour:$minute:$second"
            }
        }
            jieshushijianTv.setOnClickListener {
            mjieshushijianPicker.show()
        }
            mYundongjiluItemBean.jilushijian = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            jilushijianTv.text = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            val mjilushijianPicker = DatimePicker(this@AddOrUpdateActivity).apply {
            wheelLayout.setDateFormatter(BirthdayFormatter())
            wheelLayout.setTimeFormatter(UnitTimeFormatter())
            wheelLayout.setRange(DatimeEntity.yearOnFuture(-100), DatimeEntity.yearOnFuture(50), DatimeEntity.now())
            setOnDatimePickedListener { year, month, day, hour, minute, second ->
                jilushijianTv.text = "$year-$month-$day $hour:$minute:$second"
                mYundongjiluItemBean.jilushijian="$year-$month-$day $hour:$minute:$second"
            }
        }
            jilushijianTv.setOnClickListener {
            mjilushijianPicker.show()
        }

            submitBtn.setOnClickListener{/*提交*/
                submit()
            }
            setData()
    }

    lateinit var mUserBean:LinkedTreeMap<String, Any>/*当前用户数据*/

    override fun initData() {
        super.initData()
        UserRepository.session<Any>().observeKt {
            it.getOrNull()?.let {
                it.data.toConversion<LinkedTreeMap<String, Any>>()?.let {
                    mUserBean = it
                    it["touxiang"]?.let { it1 -> StorageUtil.encode(CommonBean.HEAD_URL_KEY, it1) }
                    /**ss读取*/
                    if (mYundongjiluItemBean.zhanghao.isNullOrEmpty()){
                        mYundongjiluItemBean.zhanghao = it["zhanghao"]?.toString()?:""
                    }
                    binding.zhanghaoEt.keyListener = null
                    if (mYundongjiluItemBean.xingming.isNullOrEmpty()){
                        mYundongjiluItemBean.xingming = it["xingming"]?.toString()?:""
                    }
                    binding.xingmingEt.keyListener = null
                    binding.setData()
                    if (mStatusColumnName=="&jiangpinmingcheng&"){
                        binding.submit()
                    }
                }
            }
        }

        (mId>0).yes {/*更新操作*/
            HomeRepository.info<YundongjiluItemBean>("yundongjilu",mId).observeKt {
                it.getOrNull()?.let {
                    mYundongjiluItemBean = it.data
                    mYundongjiluItemBean.id = mId
                    binding.setData()
                }
            }
        }
        if (mCrossTable.isNotNullOrEmpty()){/*跨表*/
            mCrossObj.javaClass.declaredFields.any{it.name == "mubiaomingcheng"}.yes {
                mYundongjiluItemBean.mubiaomingcheng = mCrossObj.javaClass.getDeclaredField("mubiaomingcheng").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            mCrossObj.javaClass.declaredFields.any{it.name == "yundongleixing"}.yes {
                mYundongjiluItemBean.yundongleixing = mCrossObj.javaClass.getDeclaredField("yundongleixing").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            mCrossObj.javaClass.declaredFields.any{it.name == "kaishishijian"}.yes {
                mYundongjiluItemBean.kaishishijian = mCrossObj.javaClass.getDeclaredField("kaishishijian").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            mCrossObj.javaClass.declaredFields.any{it.name == "jieshushijian"}.yes {
                mYundongjiluItemBean.jieshushijian = mCrossObj.javaClass.getDeclaredField("jieshushijian").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            mCrossObj.javaClass.declaredFields.any{it.name == "yundongshizhang"}.yes {
                mYundongjiluItemBean.yundongshizhang = mCrossObj.javaClass.getDeclaredField("yundongshizhang").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            mCrossObj.javaClass.declaredFields.any{it.name == "xiaohaokaluli"}.yes {
                mYundongjiluItemBean.xiaohaokaluli = mCrossObj.javaClass.getDeclaredField("xiaohaokaluli").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            mCrossObj.javaClass.declaredFields.any{it.name == "jilushijian"}.yes {
                mYundongjiluItemBean.jilushijian = mCrossObj.javaClass.getDeclaredField("jilushijian").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            mCrossObj.javaClass.declaredFields.any{it.name == "zhanghao"}.yes {
                mYundongjiluItemBean.zhanghao = mCrossObj.javaClass.getDeclaredField("zhanghao").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            mCrossObj.javaClass.declaredFields.any{it.name == "xingming"}.yes {
                mYundongjiluItemBean.xingming = mCrossObj.javaClass.getDeclaredField("xingming").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            if (mStatusColumnName=="&yuyuexuanzuo&"){
                if (mYundongjiluItemBean.javaClass.declaredFields.any{it.name == "timeslot"}){
                    mYundongjiluItemBean.javaClass.getDeclaredField("timeslot").also { it.isAccessible=true }.let {
                        it.set(mYundongjiluItemBean,mStatusColumnValue.split("[]")[2])
                    }
                }
                if (mYundongjiluItemBean.javaClass.declaredFields.any{it.name == "seatnum"}){
                    mYundongjiluItemBean.javaClass.getDeclaredField("seatnum").also { it.isAccessible=true }.let {
                        it.set(mYundongjiluItemBean,mStatusColumnValue.split("[]")[0])
                    }
                }
                if (mYundongjiluItemBean.javaClass.declaredFields.any{it.name == "reservationdate"}){
                    mYundongjiluItemBean.javaClass.getDeclaredField("reservationdate").also { it.isAccessible=true }.let {
                        it.set(mYundongjiluItemBean,mStatusColumnValue.split("[]")[1])
                    }
                }
            }
        }
        binding.setData()
    }

    /**验证*/
    private fun YundongjiluaddorupdateLayoutBinding.submit() {
        mYundongjiluItemBean.mubiaomingcheng = mubiaomingchengEt.text.toString()
        mYundongjiluItemBean.yundongshizhang = yundongshizhangEt.text.toString()
        mYundongjiluItemBean.xiaohaokaluli = xiaohaokaluliEt.text.toString()
        mYundongjiluItemBean.zhanghao = zhanghaoEt.text.toString()
        mYundongjiluItemBean.xingming = xingmingEt.text.toString()
        var crossuserid:Long = 0
        var crossrefid:Long = 0
        var crossoptnum:Int = 0
        if (mStatusColumnName.isNotNullOrEmpty()){
            if (!mStatusColumnName.startsWith("[")){
                mCrossObj.javaClass.declaredFields.any{it.name == mStatusColumnName}.yes {
                    mCrossObj.javaClass.getDeclaredField(mStatusColumnName).also { it.isAccessible=true }.set(mCrossObj,mStatusColumnValue)
                    UserRepository.update(mCrossTable,mCrossObj).observeForever {  }
                }
            }else{
                crossuserid = Utils.getUserId()
                mCrossObj.javaClass.declaredFields.any{it.name == "id"}.yes {
                    crossrefid =mCrossObj.javaClass.getDeclaredField("id").also { it.isAccessible=true }.get(mCrossObj).toString().toLong()
                }
                crossoptnum = mStatusColumnName.replace("[","").replace("]","").toIntOrNull()?:0
            }
        }

        if (crossuserid>0 && crossrefid>0){
            mYundongjiluItemBean.javaClass.declaredFields.any{it.name == "crossuserid"}.yes {
                mYundongjiluItemBean.javaClass.getDeclaredField("crossuserid").also { it.isAccessible=true }.set(mYundongjiluItemBean,crossuserid)
            }
            mYundongjiluItemBean.javaClass.declaredFields.any{it.name == "crossrefid"}.yes {
                mYundongjiluItemBean.javaClass.getDeclaredField("crossrefid").also { it.isAccessible=true }.set(mYundongjiluItemBean,crossrefid)
            }
            if (mStatusColumnName=="&jiangpinmingcheng&"){
                mYundongjiluItemBean.javaClass.declaredFields.any{it.name == "jiangpinmingcheng"}.yes {
                    mYundongjiluItemBean.javaClass.getDeclaredField("jiangpinmingcheng").also { it.isAccessible=true }.set(mYundongjiluItemBean,mStatusColumnValue)
                }
                crossCal()
            }else{
                HomeRepository.list<YundongjiluItemBean>("yundongjilu", mapOf("page" to "1","limit" to "10","crossuserid" to crossuserid.toString(),"crossrefid" to crossrefid.toString())).observeKt{
                    it.getOrNull()?.let {
                        if (it.data.list.size>=crossoptnum){
                            mTips.showToast()
                        }else{
                            crossCal()
                        }
                    }
                }
            }
        }else{
            crossCal()
        }

}
    private fun crossCal(){/*更新跨表数据*/
        addOrUpdate()
    }
    private fun addOrUpdate(){/*更新或添加*/
        if (mYundongjiluItemBean.id>0){
            UserRepository.update("yundongjilu",mYundongjiluItemBean).observeKt{
            it.getOrNull()?.let {
                "提交成功".showToast()
                finish()
            }
        }
        }else{
            HomeRepository.add<YundongjiluItemBean>("yundongjilu",mYundongjiluItemBean).observeKt{
            it.getOrNull()?.let {
                if (mStatusColumnName!="&jiangpinmingcheng&") {
                    "提交成功".showToast()
                }
                finish()
            }
        }
        }
    }


    private fun YundongjiluaddorupdateLayoutBinding.setData(){
        if (mYundongjiluItemBean.mubiaomingcheng.isNotNullOrEmpty()){
            mubiaomingchengEt.setText(mYundongjiluItemBean.mubiaomingcheng.toString())
        }
        if (mYundongjiluItemBean.yundongleixing.isNotNullOrEmpty()){
            yundongleixingBs.text =mYundongjiluItemBean.yundongleixing
        }
        if (mYundongjiluItemBean.yundongshizhang.isNotNullOrEmpty()){
            yundongshizhangEt.setText(mYundongjiluItemBean.yundongshizhang.toString())
        }
        if (mYundongjiluItemBean.xiaohaokaluli.isNotNullOrEmpty()){
            xiaohaokaluliEt.setText(mYundongjiluItemBean.xiaohaokaluli.toString())
        }
        if (mYundongjiluItemBean.zhanghao.isNotNullOrEmpty()){
            zhanghaoEt.setText(mYundongjiluItemBean.zhanghao.toString())
        }
        if (mYundongjiluItemBean.xingming.isNotNullOrEmpty()){
            xingmingEt.setText(mYundongjiluItemBean.xingming.toString())
        }
    }
}