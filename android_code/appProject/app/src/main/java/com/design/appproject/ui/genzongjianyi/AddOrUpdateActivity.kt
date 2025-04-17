package com.design.appproject.ui.genzongjianyi

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
import com.design.appproject.bean.GenzongjianyiItemBean
import com.blankj.utilcode.constant.TimeConstants
import com.design.appproject.ext.afterTextChanged
import com.design.appproject.R
import android.os.Bundle
import android.view.View
import com.design.appproject.bean.JiankangmubiaoItemBean
import com.design.appproject.databinding.GenzongjianyiaddorupdateLayoutBinding
import com.design.appproject.ext.load
import android.text.InputType

/**
 * 跟踪建议新增或修改类
 */
@Route(path = CommonArouteApi.PATH_ACTIVITY_ADDORUPDATE_GENZONGJIANYI)
class AddOrUpdateActivity:BaseBindingActivity<GenzongjianyiaddorupdateLayoutBinding>() {

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
    var mGenzongjianyiItemBean = GenzongjianyiItemBean()

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
        setBarTitle("跟踪建议")
        setBarColor("#FFFFFF","black")
        if (mRefid>0){/*如果上一级页面传递了refid，获取改refid数据信息*/
            if (mGenzongjianyiItemBean.javaClass.declaredFields.any{it.name == "refid"}){
                mGenzongjianyiItemBean.javaClass.getDeclaredField("refid").also { it.isAccessible=true }.let {
                    it.set(mGenzongjianyiItemBean,mRefid)
                }
            }
            if (mGenzongjianyiItemBean.javaClass.declaredFields.any{it.name == "nickname"}){
                mGenzongjianyiItemBean.javaClass.getDeclaredField("nickname").also { it.isAccessible=true }.let {
                    it.set(mGenzongjianyiItemBean,StorageUtil.decodeString(CommonBean.USERNAME_KEY)?:"")
                }
            }
        }
        if (Utils.isLogin() && mGenzongjianyiItemBean.javaClass.declaredFields.any{it.name == "userid"}){/*如果有登陆，获取登陆后保存的userid*/
            mGenzongjianyiItemBean.javaClass.getDeclaredField("userid").also { it.isAccessible=true }.let {
                it.set(mGenzongjianyiItemBean,Utils.getUserId())
            }
        }
        binding.initView()

    }

    fun GenzongjianyiaddorupdateLayoutBinding.initView(){
            mGenzongjianyiItemBean.gengxinshijian = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            gengxinshijianTv.text = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            val mgengxinshijianPicker = DatimePicker(this@AddOrUpdateActivity).apply {
            wheelLayout.setDateFormatter(BirthdayFormatter())
            wheelLayout.setTimeFormatter(UnitTimeFormatter())
            wheelLayout.setRange(DatimeEntity.yearOnFuture(-100), DatimeEntity.yearOnFuture(50), DatimeEntity.now())
            setOnDatimePickedListener { year, month, day, hour, minute, second ->
                gengxinshijianTv.text = "$year-$month-$day $hour:$minute:$second"
                mGenzongjianyiItemBean.gengxinshijian="$year-$month-$day $hour:$minute:$second"
            }
        }
            gengxinshijianTv.setOnClickListener {
            mgengxinshijianPicker.show()
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
                    binding.setData()
                    if (mStatusColumnName=="&jiangpinmingcheng&"){
                        binding.submit()
                    }
                }
            }
        }

        (mId>0).yes {/*更新操作*/
            HomeRepository.info<GenzongjianyiItemBean>("genzongjianyi",mId).observeKt {
                it.getOrNull()?.let {
                    mGenzongjianyiItemBean = it.data
                    mGenzongjianyiItemBean.id = mId
                    binding.setData()
                }
            }
        }
        if (mCrossTable.isNotNullOrEmpty()){/*跨表*/
            mCrossObj.javaClass.declaredFields.any{it.name == "zhanghao"}.yes {
                mGenzongjianyiItemBean.zhanghao = mCrossObj.javaClass.getDeclaredField("zhanghao").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            mCrossObj.javaClass.declaredFields.any{it.name == "xingming"}.yes {
                mGenzongjianyiItemBean.xingming = mCrossObj.javaClass.getDeclaredField("xingming").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            mCrossObj.javaClass.declaredFields.any{it.name == "yundongmubiao"}.yes {
                mGenzongjianyiItemBean.yundongmubiao = mCrossObj.javaClass.getDeclaredField("yundongmubiao").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            mCrossObj.javaClass.declaredFields.any{it.name == "diaozhengmubiao"}.yes {
                mGenzongjianyiItemBean.diaozhengmubiao = mCrossObj.javaClass.getDeclaredField("diaozhengmubiao").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            mCrossObj.javaClass.declaredFields.any{it.name == "diaozhengjianyi"}.yes {
                mGenzongjianyiItemBean.diaozhengjianyi = mCrossObj.javaClass.getDeclaredField("diaozhengjianyi").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            mCrossObj.javaClass.declaredFields.any{it.name == "gengxinshijian"}.yes {
                mGenzongjianyiItemBean.gengxinshijian = mCrossObj.javaClass.getDeclaredField("gengxinshijian").also { it.isAccessible=true }.get(mCrossObj) as  String            }
            if (mStatusColumnName=="&yuyuexuanzuo&"){
                if (mGenzongjianyiItemBean.javaClass.declaredFields.any{it.name == "timeslot"}){
                    mGenzongjianyiItemBean.javaClass.getDeclaredField("timeslot").also { it.isAccessible=true }.let {
                        it.set(mGenzongjianyiItemBean,mStatusColumnValue.split("[]")[2])
                    }
                }
                if (mGenzongjianyiItemBean.javaClass.declaredFields.any{it.name == "seatnum"}){
                    mGenzongjianyiItemBean.javaClass.getDeclaredField("seatnum").also { it.isAccessible=true }.let {
                        it.set(mGenzongjianyiItemBean,mStatusColumnValue.split("[]")[0])
                    }
                }
                if (mGenzongjianyiItemBean.javaClass.declaredFields.any{it.name == "reservationdate"}){
                    mGenzongjianyiItemBean.javaClass.getDeclaredField("reservationdate").also { it.isAccessible=true }.let {
                        it.set(mGenzongjianyiItemBean,mStatusColumnValue.split("[]")[1])
                    }
                }
            }
        }
        binding.setData()
    }

    /**验证*/
    private fun GenzongjianyiaddorupdateLayoutBinding.submit() {
        mGenzongjianyiItemBean.zhanghao = zhanghaoEt.text.toString()
        mGenzongjianyiItemBean.xingming = xingmingEt.text.toString()
        mGenzongjianyiItemBean.yundongmubiao = yundongmubiaoEt.text.toString()
        mGenzongjianyiItemBean.diaozhengmubiao = diaozhengmubiaoEt.text.toString()
        mGenzongjianyiItemBean.diaozhengjianyi = diaozhengjianyiEt.text.toString()
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
            mGenzongjianyiItemBean.javaClass.declaredFields.any{it.name == "crossuserid"}.yes {
                mGenzongjianyiItemBean.javaClass.getDeclaredField("crossuserid").also { it.isAccessible=true }.set(mGenzongjianyiItemBean,crossuserid)
            }
            mGenzongjianyiItemBean.javaClass.declaredFields.any{it.name == "crossrefid"}.yes {
                mGenzongjianyiItemBean.javaClass.getDeclaredField("crossrefid").also { it.isAccessible=true }.set(mGenzongjianyiItemBean,crossrefid)
            }
            if (mStatusColumnName=="&jiangpinmingcheng&"){
                mGenzongjianyiItemBean.javaClass.declaredFields.any{it.name == "jiangpinmingcheng"}.yes {
                    mGenzongjianyiItemBean.javaClass.getDeclaredField("jiangpinmingcheng").also { it.isAccessible=true }.set(mGenzongjianyiItemBean,mStatusColumnValue)
                }
                crossCal()
            }else{
                HomeRepository.list<GenzongjianyiItemBean>("genzongjianyi", mapOf("page" to "1","limit" to "10","crossuserid" to crossuserid.toString(),"crossrefid" to crossrefid.toString())).observeKt{
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
        if (mGenzongjianyiItemBean.id>0){
            UserRepository.update("genzongjianyi",mGenzongjianyiItemBean).observeKt{
            it.getOrNull()?.let {
                "提交成功".showToast()
                finish()
            }
        }
        }else{
            HomeRepository.add<GenzongjianyiItemBean>("genzongjianyi",mGenzongjianyiItemBean).observeKt{
            it.getOrNull()?.let {
                if (mStatusColumnName!="&jiangpinmingcheng&") {
                    "提交成功".showToast()
                }
                finish()
            }
        }
        }
    }


    private fun GenzongjianyiaddorupdateLayoutBinding.setData(){
        if (mGenzongjianyiItemBean.zhanghao.isNotNullOrEmpty()){
            zhanghaoEt.setText(mGenzongjianyiItemBean.zhanghao.toString())
        }
        if (mGenzongjianyiItemBean.xingming.isNotNullOrEmpty()){
            xingmingEt.setText(mGenzongjianyiItemBean.xingming.toString())
        }
        if (mGenzongjianyiItemBean.yundongmubiao.isNotNullOrEmpty()){
            yundongmubiaoEt.setText(mGenzongjianyiItemBean.yundongmubiao.toString())
        }
        if (mGenzongjianyiItemBean.diaozhengmubiao.isNotNullOrEmpty()){
            diaozhengmubiaoEt.setText(mGenzongjianyiItemBean.diaozhengmubiao.toString())
        }
        if (mGenzongjianyiItemBean.diaozhengjianyi.isNotNullOrEmpty()){
            diaozhengjianyiEt.setText(mGenzongjianyiItemBean.diaozhengjianyi.toString())
        }
    }
}