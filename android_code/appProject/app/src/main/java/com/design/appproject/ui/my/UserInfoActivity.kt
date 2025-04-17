package com.design.appproject.ui.my

import android.content.Intent
import android.widget.CheckBox
import android.widget.RadioButton
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.UriUtils
import com.design.appproject.base.BaseBindingActivity
import com.design.appproject.base.CommonArouteApi
import com.design.appproject.databinding.ActivityUserInfoLayoutBinding
import com.design.appproject.logic.repository.UserRepository
import com.google.gson.internal.LinkedTreeMap
import com.design.appproject.ext.load
import androidx.core.view.isVisible
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.RegexUtils
import androidx.core.view.children
import com.blankj.utilcode.util.TimeUtils
import com.design.appproject.base.CommonBean
import com.design.appproject.base.EventBus
import com.design.appproject.ext.postEvent
import com.design.appproject.logic.repository.HomeRepository
import com.design.appproject.widget.BottomSpinner
import com.github.gzuliyujiang.wheelpicker.DatePicker
import com.github.gzuliyujiang.wheelpicker.DatimePicker
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity
import com.github.gzuliyujiang.wheelpicker.entity.DatimeEntity
import com.github.gzuliyujiang.wheelpicker.impl.BirthdayFormatter
import com.github.gzuliyujiang.wheelpicker.impl.UnitTimeFormatter
import com.union.union_basic.ext.*
import com.union.union_basic.image.selector.SmartPictureSelector
import com.union.union_basic.utils.StorageUtil
import java.io.File
import java.text.SimpleDateFormat

/**
 *用户信息界面
 */
@Route(path = CommonArouteApi.PATH_ACTIVITY_USER_INFO)
class UserInfoActivity: BaseBindingActivity<ActivityUserInfoLayoutBinding>() {

    override fun initEvent() {
        setBarTitle("用户信息")
        setBarColor("#FFFFFF","black")
        binding.apply {
            usersusernameLl.isVisible = "users" == CommonBean.tableName
            userspasswordLl.isVisible = "users" == CommonBean.tableName
            usersroleLl.isVisible = "users" == CommonBean.tableName
            yonghuzhanghaoLl.isVisible = "yonghu" == CommonBean.tableName
            yonghuxingmingLl.isVisible = "yonghu" == CommonBean.tableName
            yonghutouxiangLl.isVisible = "yonghu" == CommonBean.tableName
            yonghutouxiangLl.setOnClickListener {
            SmartPictureSelector.openPicture(this@UserInfoActivity) {
                val path = it[0]
                showLoading("上传中...")
                UserRepository.upload(File(path), "touxiang").observeKt{
                    it.getOrNull()?.let {
                        yonghutouxiangIfv.load(this@UserInfoActivity, "file/"+it.file)
                        mSessionInfo["touxiang"] = "file/" + it.file
                    }
                }
            }
        }
            yonghuxingbieLl.isVisible = "yonghu" == CommonBean.tableName
            yonghuxingbieBs.setOptions("男,女".split(","),"请选择性别",
            listener = object : BottomSpinner.OnItemSelectedListener {
                override fun onItemSelected(position: Int, content: String) {
                    super.onItemSelected(position, content)
                    yonghuxingbieBs.text = content
                    mSessionInfo["xingbie"] = content
                }
            })
            yonghunianlingLl.isVisible = "yonghu" == CommonBean.tableName
            yonghushoujiLl.isVisible = "yonghu" == CommonBean.tableName
            saveBtn.setOnClickListener { /*保存*/
                verify().yes {
                    UserRepository.update(CommonBean.tableName,mSessionInfo).observeKt {
                        it.getOrNull()?.let {
                            StorageUtil.encode(CommonBean.HEAD_URL_KEY,mSessionInfo["touxiang"].toString())
                            if(CommonBean.tableName == "yonghu"){
                                StorageUtil.encode(CommonBean.USERNAME_KEY,mSessionInfo["zhanghao"].toString())
                            }
                            postEvent(EventBus.USER_INFO_UPDATED,true)
                            "修改成功".showToast()
                        }
                    }
                }
            }
            logoutBtn.setOnClickListener { /*退出登录*/
                StorageUtil.encode(CommonBean.USER_ID_KEY, 0)
                StorageUtil.encode(CommonBean.VIP_KEY, false)
                StorageUtil.encode(CommonBean.HEAD_URL_KEY, "")
                StorageUtil.encode(CommonBean.LOGIN_USER_OPTIONS, "")
                StorageUtil.encode(CommonBean.USERNAME_KEY,"")
                StorageUtil.encode(CommonBean.TOKEN_KEY, "")
                StorageUtil.encode(CommonBean.TABLE_NAME_KEY, "")
                CommonBean.tableName = ""
                postEvent(EventBus.USER_INFO_UPDATED,false)
                finish()
                ARouter.getInstance().build(CommonArouteApi.PATH_ACTIVITY_MAIN).navigation()
            }
        }
    }

    private fun ActivityUserInfoLayoutBinding.verify():Boolean{
    if ("users" == CommonBean.tableName){
            mSessionInfo["username"] = usersusernameEt.text.toString()
            mSessionInfo["password"] = userspasswordEt.text.toString()
            mSessionInfo["role"] = usersroleEt.text.toString()
            mSessionInfo["role"].toString().isNullOrEmpty().yes {
                "角色不能为空".showToast()
                return false
            }
        }
    if ("yonghu" == CommonBean.tableName){
            mSessionInfo["zhanghao"] = yonghuzhanghaoEt.text.toString()
            mSessionInfo["xingming"] = yonghuxingmingEt.text.toString()
            mSessionInfo["nianling"] = yonghunianlingEt.text.toString().replace(".0","")
            mSessionInfo["nianling"].toString().isNullOrEmpty().yes {
                "年龄不能为空".showToast()
                return false
            }
            mSessionInfo["shouji"] = yonghushoujiEt.text.toString()
            mSessionInfo["shouji"].toString().isNullOrEmpty().yes {
                "手机不能为空".showToast()
                return false
            }
            mSessionInfo["shouji"] = yonghushoujiEt.text.toString()
            RegexUtils.isMobileExact(mSessionInfo["shouji"].toString()).no {
                mSessionInfo["shouji"].toString().isNotNullOrEmpty().yes {
                    "手机应输入手机格式".showToast()
                    return false
                }
            }
        }
        return true
    }

    lateinit var mSessionInfo:LinkedTreeMap<String, Any>

    override fun initData() {
        super.initData()
        UserRepository.session<Any>().observeKt {
            it.getOrNull()?.let {
                it.data.toConversion<LinkedTreeMap<String, Any>>()?.let {
                    mSessionInfo= it
                    binding.setData(it)
                }
            }
        }
    }

    private fun ActivityUserInfoLayoutBinding.setData(session:LinkedTreeMap<String, Any>){
        usersusernameEt.setText(session["username"].toString())
        userspasswordEt.setText(session["password"].toString())
        usersroleEt.setText(session["role"].toString())
        yonghuzhanghaoEt.setText(session["zhanghao"].toString())
        yonghuzhanghaoEt.keyListener = null
        yonghuxingmingEt.setText(session["xingming"].toString())
        yonghutouxiangIfv.load(this@UserInfoActivity, session["touxiang"].toString())
        yonghuxingbieBs.text = session["xingbie"].toString()
        yonghunianlingEt.setText(session["nianling"].toString().replace(".0",""))
        yonghushoujiEt.setText(session["shouji"].toString())
    }

}