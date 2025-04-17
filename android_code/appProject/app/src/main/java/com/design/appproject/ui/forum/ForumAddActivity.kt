package com.design.appproject.ui.forum

import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.TimeUtils
import com.design.appproject.R
import com.design.appproject.base.BaseBindingActivity
import com.design.appproject.base.CommonArouteApi
import com.design.appproject.base.CommonBean
import com.design.appproject.bean.forum.ForumItemBean
import com.design.appproject.databinding.ActivityForumAddLayoutBinding
import com.design.appproject.ext.UrlPrefix
import com.design.appproject.logic.viewmodel.forum.ForumModel
import com.union.union_basic.ext.marginKTX
import com.union.union_basic.ext.showToast
import com.union.union_basic.image.selector.SmartPictureSelector
import com.union.union_basic.utils.StorageUtil
import java.io.File

/**
 * 新增论坛帖子页
 * */
@Route(path = CommonArouteApi.PATH_ACTIVITY_ADDORUPDATE_FORUM)
class ForumAddActivity: BaseBindingActivity<ActivityForumAddLayoutBinding>() {

    @JvmField
    @Autowired
    var mId: Long = 0 /*帖子id，有代表修改*/

    private val mFroumModel by viewModels<ForumModel>()

    override fun initEvent() {
        binding.apply {
            activityTitleBar.mRightTextView?.let {
                it.isVisible =true
                it.setTextColor(ColorUtils.getColor(R.color.common_red))
                it.text = "发布"
                it.setOnClickListener {
                    showLoading()
                    var forumContent=richEt.html
                    if (forumContent.isNullOrEmpty()){
                        "请输入内容".showToast()
                        return@setOnClickListener
                    }
                    val forumItemBean = ForumItemBean(
                        isdone = optionsBs.text.toString(),
                        title = titleEt.text.toString(),
                        content = forumContent,
                        userid = StorageUtil.decodeLong(CommonBean.USER_ID_KEY,0),
                        addtime = TimeUtils.getNowString(),
                        username = StorageUtil.decodeString(CommonBean.USERNAME_KEY)?:"",)
                    if (mId>0){
                        forumItemBean.id = mId
                        mFroumModel.updateForum(forumItemBean)
                    }else{
                        mFroumModel.saveForum(forumItemBean)
                    }
                }
            }
           setBarTitle("我的发布")
           optionsBs.setOptions(listOf("开放","关闭"))
            KeyboardUtils.registerSoftInputChangedListener(this@ForumAddActivity){ height->
                editHsv.marginKTX(bottom = height)
            }

            actionBold.setOnClickListener {
                richEt.setBold()
            }
            actionItalic.setOnClickListener {
                richEt.setItalic()
            }
            actionStrikethrough.setOnClickListener {
                richEt.setStrikeThrough()
            }
            actionUnderline.setOnClickListener {
                richEt.setUnderline()
            }
            actionHeading1.setOnClickListener {
                richEt.setHeading(1)
            }
            actionHeading2.setOnClickListener {
                richEt.setHeading(2)
            }
            actionHeading3.setOnClickListener {
                richEt.setHeading(3)
            }
            actionHeading4.setOnClickListener {
                richEt.setHeading(4)
            }
            actionHeading5.setOnClickListener {
                richEt.setHeading(5)
            }
            actionIndent.setOnClickListener {
                richEt.setIndent()
            }
            actionOutdent.setOnClickListener {
                richEt.setOutdent()
            }
            actionAlignCenter.setOnClickListener {
                richEt.setAlignCenter()
            }
            actionAlignLeft.setOnClickListener {
                richEt.setAlignLeft()
            }
            actionAlignRight.setOnClickListener {
                richEt.setAlignRight()
            }
            actionInsertBullets.setOnClickListener {
                richEt.setBullets()
            }
            actionInsertNumbers.setOnClickListener {
                richEt.setNumbers()
            }
            actionInsertImage.setOnClickListener {
                SmartPictureSelector.openPicture(this@ForumAddActivity) {
                    val path = it[0]
                    showLoading("上传中...")
                    mFroumModel.upload(File(path), )
                }
            }
        }
    }

    override fun initData() {
        super.initData()
        if (mId>0){
            mFroumModel.forumInfo(mId)
            mFroumModel.forumInfoLiveData.observeKt {
                it.getOrNull()?.let {
                    binding.apply {
                        titleEt.setText(it.data.title)
                        optionsBs.setText(it.data.isdone)
                        richEt.html = it.data.content
                    }
                }
            }
            mFroumModel.updateForumLiveData.observeKt {
                it.getOrNull()?.let {
                    "修改成功".showToast()
                    finish()
                }
            }
        }
        mFroumModel.saveForumLiveData.observeKt {
            it.getOrNull()?.let {
                "发布成功".showToast()
                finish()
            }
        }
        mFroumModel.uploadLiveData.observeKt {
            it.getOrNull()?.let {
                binding.richEt.insertImage(UrlPrefix.URL_PREFIX+"file/" + it.file, "dachshund", 320)
            }
        }
    }
}