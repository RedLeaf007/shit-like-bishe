package com.design.appproject.ui.jiankangmubiao

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
import com.design.appproject.bean.JiankangmubiaoItemBean
import com.blankj.utilcode.constant.TimeConstants
import com.design.appproject.ext.afterTextChanged
import com.design.appproject.databinding.JiankangmubiaoaddorupdateLayoutBinding
import com.design.appproject.ext.load
import android.text.InputType

/**
 * 健康目标新增或修改类
 */
@Route(path = CommonArouteApi.PATH_ACTIVITY_ADDORUPDATE_JIANKANGMUBIAO)
class AddOrUpdateActivity:BaseBindingActivity<JiankangmubiaoaddorupdateLayoutBinding>() {

    @JvmField
    @Autowired
    var mId: Long = 0L /*id*/

    @JvmField
    @Autowired
    var mRefid: Long = 0 /*refid数据*/

    /**上传数据*/
    var mJiankangmubiaoItemBean = JiankangmubiaoItemBean()

    override fun initEvent() {
        setBarTitle("健康目标")
        setBarColor("#FFFFFF","black")
        if (mRefid>0){/*如果上一级页面传递了refid，获取改refid数据信息*/
            if (mJiankangmubiaoItemBean.javaClass.declaredFields.any{it.name == "refid"}){
                mJiankangmubiaoItemBean.javaClass.getDeclaredField("refid").also { it.isAccessible=true }.let {
                    it.set(mJiankangmubiaoItemBean,mRefid)
                }
            }
            if (mJiankangmubiaoItemBean.javaClass.declaredFields.any{it.name == "nickname"}){
                mJiankangmubiaoItemBean.javaClass.getDeclaredField("nickname").also { it.isAccessible=true }.let {
                    it.set(mJiankangmubiaoItemBean,StorageUtil.decodeString(CommonBean.USERNAME_KEY)?:"")
                }
            }
        }
        if (Utils.isLogin() && mJiankangmubiaoItemBean.javaClass.declaredFields.any{it.name == "userid"}){/*如果有登陆，获取登陆后保存的userid*/
            mJiankangmubiaoItemBean.javaClass.getDeclaredField("userid").also { it.isAccessible=true }.let {
                it.set(mJiankangmubiaoItemBean,Utils.getUserId())
            }
        }
        binding.initView()

    }

    fun JiankangmubiaoaddorupdateLayoutBinding.initView(){
            mJiankangmubiaoItemBean.zhidingshijian = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            zhidingshijianTv.text = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-dd hh:mm:ss"))
            val mzhidingshijianPicker = DatimePicker(this@AddOrUpdateActivity).apply {
            wheelLayout.setDateFormatter(BirthdayFormatter())
            wheelLayout.setTimeFormatter(UnitTimeFormatter())
            wheelLayout.setRange(DatimeEntity.yearOnFuture(-100), DatimeEntity.yearOnFuture(50), DatimeEntity.now())
            setOnDatimePickedListener { year, month, day, hour, minute, second ->
                zhidingshijianTv.text = "$year-$month-$day $hour:$minute:$second"
                mJiankangmubiaoItemBean.zhidingshijian="$year-$month-$day $hour:$minute:$second"
            }
        }
            zhidingshijianTv.setOnClickListener {
            mzhidingshijianPicker.show()
        }
            val mjihuakaishiPicker = DatePicker(this@AddOrUpdateActivity).apply {
                wheelLayout.setDateFormatter(BirthdayFormatter())
                wheelLayout.setRange(DateEntity.target(1923, 1, 1),DateEntity.target(2050, 12, 31), DateEntity.today())
                setOnDatePickedListener { year, month, day ->
                    jihuakaishiTv.text = "$year-$month-$day"
                    mJiankangmubiaoItemBean.jihuakaishi="$year-$month-$day"
                }
            }
            jihuakaishiTv.setOnClickListener {
                mjihuakaishiPicker.show()
            }
            val mjihuajieshuPicker = DatePicker(this@AddOrUpdateActivity).apply {
                wheelLayout.setDateFormatter(BirthdayFormatter())
                wheelLayout.setRange(DateEntity.target(1923, 1, 1),DateEntity.target(2050, 12, 31), DateEntity.today())
                setOnDatePickedListener { year, month, day ->
                    jihuajieshuTv.text = "$year-$month-$day"
                    mJiankangmubiaoItemBean.jihuajieshu="$year-$month-$day"
                }
            }
            jihuajieshuTv.setOnClickListener {
                mjihuajieshuPicker.show()
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
                    if (mJiankangmubiaoItemBean.zhanghao.isNullOrEmpty()){
                        mJiankangmubiaoItemBean.zhanghao = it["zhanghao"]?.toString()?:""
                    }
                    binding.zhanghaoEt.keyListener = null
                    if (mJiankangmubiaoItemBean.xingming.isNullOrEmpty()){
                        mJiankangmubiaoItemBean.xingming = it["xingming"]?.toString()?:""
                    }
                    binding.xingmingEt.keyListener = null
                    binding.setData()
                }
            }
        }

        (mId>0).yes {/*更新操作*/
            HomeRepository.info<JiankangmubiaoItemBean>("jiankangmubiao",mId).observeKt {
                it.getOrNull()?.let {
                    mJiankangmubiaoItemBean = it.data
                    mJiankangmubiaoItemBean.id = mId
                    binding.setData()
                }
            }
        }
        binding.setData()
    }

    /**验证*/
    private fun JiankangmubiaoaddorupdateLayoutBinding.submit() {
        mJiankangmubiaoItemBean.mubiaomingcheng = mubiaomingchengEt.text.toString()
        mJiankangmubiaoItemBean.duanlianmubiao = duanlianmubiaoEt.text.toString()
        mJiankangmubiaoItemBean.yundongmubiao = yundongmubiaoEt.text.toString()
        mJiankangmubiaoItemBean.yinshimubiao = yinshimubiaoEt.text.toString()
        mJiankangmubiaoItemBean.zhanghao = zhanghaoEt.text.toString()
        mJiankangmubiaoItemBean.xingming = xingmingEt.text.toString()
        addOrUpdate()

}
    private fun addOrUpdate(){/*更新或添加*/
        if (mJiankangmubiaoItemBean.id>0){
            UserRepository.update("jiankangmubiao",mJiankangmubiaoItemBean).observeKt{
            it.getOrNull()?.let {
                "提交成功".showToast()
                finish()
            }
        }
        }else{
            HomeRepository.add<JiankangmubiaoItemBean>("jiankangmubiao",mJiankangmubiaoItemBean).observeKt{
            it.getOrNull()?.let {
                "提交成功".showToast()
                finish()
            }
        }
        }
    }


    private fun JiankangmubiaoaddorupdateLayoutBinding.setData(){
        if (mJiankangmubiaoItemBean.mubiaomingcheng.isNotNullOrEmpty()){
            mubiaomingchengEt.setText(mJiankangmubiaoItemBean.mubiaomingcheng.toString())
        }
        if (mJiankangmubiaoItemBean.duanlianmubiao.isNotNullOrEmpty()){
            duanlianmubiaoEt.setText(mJiankangmubiaoItemBean.duanlianmubiao.toString())
        }
        if (mJiankangmubiaoItemBean.yundongmubiao.isNotNullOrEmpty()){
            yundongmubiaoEt.setText(mJiankangmubiaoItemBean.yundongmubiao.toString())
        }
        if (mJiankangmubiaoItemBean.yinshimubiao.isNotNullOrEmpty()){
            yinshimubiaoEt.setText(mJiankangmubiaoItemBean.yinshimubiao.toString())
        }
        jihuakaishiTv.text = mJiankangmubiaoItemBean.jihuakaishi
        jihuajieshuTv.text = mJiankangmubiaoItemBean.jihuajieshu
        if (mJiankangmubiaoItemBean.zhanghao.isNotNullOrEmpty()){
            zhanghaoEt.setText(mJiankangmubiaoItemBean.zhanghao.toString())
        }
        if (mJiankangmubiaoItemBean.xingming.isNotNullOrEmpty()){
            xingmingEt.setText(mJiankangmubiaoItemBean.xingming.toString())
        }
    }
}