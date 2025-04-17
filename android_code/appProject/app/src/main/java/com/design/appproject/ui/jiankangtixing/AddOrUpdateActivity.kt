package com.design.appproject.ui.jiankangtixing

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
import com.design.appproject.bean.JiankangtixingItemBean
import com.blankj.utilcode.constant.TimeConstants
import com.design.appproject.ext.afterTextChanged
import com.design.appproject.databinding.JiankangtixingaddorupdateLayoutBinding
import com.design.appproject.ext.load
import android.text.InputType

/**
 * 健康提醒新增或修改类
 */
@Route(path = CommonArouteApi.PATH_ACTIVITY_ADDORUPDATE_JIANKANGTIXING)
class AddOrUpdateActivity:BaseBindingActivity<JiankangtixingaddorupdateLayoutBinding>() {

    @JvmField
    @Autowired
    var mId: Long = 0L /*id*/

    @JvmField
    @Autowired
    var mRefid: Long = 0 /*refid数据*/

    /**上传数据*/
    var mJiankangtixingItemBean = JiankangtixingItemBean()

    override fun initEvent() {
        setBarTitle("健康提醒")
        setBarColor("#FFFFFF","black")
        if (mRefid>0){/*如果上一级页面传递了refid，获取改refid数据信息*/
            if (mJiankangtixingItemBean.javaClass.declaredFields.any{it.name == "refid"}){
                mJiankangtixingItemBean.javaClass.getDeclaredField("refid").also { it.isAccessible=true }.let {
                    it.set(mJiankangtixingItemBean,mRefid)
                }
            }
            if (mJiankangtixingItemBean.javaClass.declaredFields.any{it.name == "nickname"}){
                mJiankangtixingItemBean.javaClass.getDeclaredField("nickname").also { it.isAccessible=true }.let {
                    it.set(mJiankangtixingItemBean,StorageUtil.decodeString(CommonBean.USERNAME_KEY)?:"")
                }
            }
        }
        if (Utils.isLogin() && mJiankangtixingItemBean.javaClass.declaredFields.any{it.name == "userid"}){/*如果有登陆，获取登陆后保存的userid*/
            mJiankangtixingItemBean.javaClass.getDeclaredField("userid").also { it.isAccessible=true }.let {
                it.set(mJiankangtixingItemBean,Utils.getUserId())
            }
        }
        binding.initView()

    }

    fun JiankangtixingaddorupdateLayoutBinding.initView(){
            zhanghaoBs.let { spinner ->
            spinner.setOnClickListener {
                spinner.options.isNullOrEmpty().yes {
                    UserRepository.option("yonghu", "zhanghao", null, null,"",false).observeKt{
                        it.getOrNull()?.let {
                            spinner.setOptions(it.data, "请选择账号", false)
                            spinner.dialogShow()
                        }
                    }
                }.otherwise {
                    spinner.dialogShow()
                }
            }
            spinner.setOnItemSelectedListener(object : BottomSpinner.OnItemSelectedListener {
                override fun onItemSelected(position: Int, content: String) {
                    super.onItemSelected(position, content)
                    spinner.text = content
                    mJiankangtixingItemBean.zhanghao =content
                    UserRepository.yonghufollow("yonghu", "zhanghao", content).observeKt{
                        it.getOrNull()?.let {
                            xingmingEt.setText(it.data.xingming.toString())
                            xingmingEt.isFocusable = false
                            mJiankangtixingItemBean.xingming = it.data.xingming
                        }
                    }
                }
            })
        }
            val mtixingshijianPicker = DatePicker(this@AddOrUpdateActivity).apply {
                wheelLayout.setDateFormatter(BirthdayFormatter())
                wheelLayout.setRange(DateEntity.target(1923, 1, 1),DateEntity.target(2050, 12, 31), DateEntity.today())
                setOnDatePickedListener { year, month, day ->
                    tixingshijianTv.text = "$year-$month-$day"
                    mJiankangtixingItemBean.tixingshijian="$year-$month-$day"
                }
            }
            tixingshijianTv.setOnClickListener {
                mtixingshijianPicker.show()
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
                }
            }
        }

        (mId>0).yes {/*更新操作*/
            HomeRepository.info<JiankangtixingItemBean>("jiankangtixing",mId).observeKt {
                it.getOrNull()?.let {
                    mJiankangtixingItemBean = it.data
                    mJiankangtixingItemBean.id = mId
                    binding.setData()
                }
            }
        }
        binding.setData()
    }

    /**验证*/
    private fun JiankangtixingaddorupdateLayoutBinding.submit() {
        mJiankangtixingItemBean.xingming = xingmingEt.text.toString()
        mJiankangtixingItemBean.tixingneirong = tixingneirongEt.text.toString()
        addOrUpdate()

}
    private fun addOrUpdate(){/*更新或添加*/
        if (mJiankangtixingItemBean.id>0){
            UserRepository.update("jiankangtixing",mJiankangtixingItemBean).observeKt{
            it.getOrNull()?.let {
                "提交成功".showToast()
                finish()
            }
        }
        }else{
            HomeRepository.add<JiankangtixingItemBean>("jiankangtixing",mJiankangtixingItemBean).observeKt{
            it.getOrNull()?.let {
                "提交成功".showToast()
                finish()
            }
        }
        }
    }


    private fun JiankangtixingaddorupdateLayoutBinding.setData(){
        if (mJiankangtixingItemBean.zhanghao.isNotNullOrEmpty()){
            zhanghaoBs.text =mJiankangtixingItemBean.zhanghao
        }
        if (mJiankangtixingItemBean.xingming.isNotNullOrEmpty()){
            xingmingEt.setText(mJiankangtixingItemBean.xingming.toString())
        }
        tixingshijianTv.text = mJiankangtixingItemBean.tixingshijian
        if (mJiankangtixingItemBean.tixingneirong.isNotNullOrEmpty()){
            tixingneirongEt.setText(mJiankangtixingItemBean.tixingneirong.toString())
        }
    }
}