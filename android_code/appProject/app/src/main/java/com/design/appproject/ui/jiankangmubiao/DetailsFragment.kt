package com.design.appproject.ui.jiankangmubiao

import com.design.appproject.ui.ShhfDialog
import com.design.appproject.logic.repository.UserRepository
import com.qmuiteam.qmui.widget.QMUIRadiusImageView
import android.annotation.SuppressLint
import com.union.union_basic.utils.StorageUtil
import com.design.appproject.utils.ArouterUtils
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.ThreadUtils.runOnUiThread
import com.design.appproject.base.*
import com.design.appproject.ext.postEvent
import com.google.gson.internal.LinkedTreeMap
import android.media.MediaPlayer
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import com.google.gson.Gson
import android.view.Gravity
import android.view.ViewGroup
import com.design.appproject.widget.DetailBannerAdapter
import android.widget.*
import androidx.constraintlayout.utils.widget.ImageFilterView
import com.blankj.utilcode.util.ColorUtils
import com.design.appproject.R
import com.qmuiteam.qmui.layout.QMUILinearLayout
import com.union.union_basic.ext.*
import androidx.core.view.setMargins
import com.alibaba.android.arouter.launcher.ARouter
import com.lxj.xpopup.XPopup
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.constant.TimeConstants
import kotlinx.coroutines.*
import com.union.union_basic.network.DownloadListener
import com.union.union_basic.network.DownloadUtil
import java.io.File
import androidx.core.view.setPadding
import com.design.appproject.logic.repository.HomeRepository
import com.design.appproject.utils.Utils
import java.util.*
import kotlin.concurrent.timerTask
import com.design.appproject.ext.load
import com.design.appproject.logic.viewmodel.jiankangmubiao.DetailsViewModel
import androidx.activity.viewModels
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.design.appproject.databinding.JiankangmubiaocommonDetailsLayoutBinding
import com.design.appproject.bean.*
import com.design.appproject.ui.CommentsAdatper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.view.isVisible
import android.text.Html
import com.design.appproject.widget.MyTextView
import com.design.appproject.widget.MyFlexBoxLayout
import com.design.appproject.widget.MyImageView
import android.view.ContextThemeWrapper
import com.google.android.flexbox.FlexWrap
import com.union.union_basic.image.loader.GlideLoader.load

/**
 * 健康目标详情页
 */
@Route(path = CommonArouteApi.PATH_FRAGMENT_DETAILS_JIANKANGMUBIAO)
class DetailsFragment : BaseBindingFragment<JiankangmubiaocommonDetailsLayoutBinding>() {

    @JvmField
    @Autowired
    var mId: Long = 0 /*id*/

    @JvmField
    @Autowired
    var mIsBack: Boolean = false /*是否用户后台进入*/

    private val mDetailsViewModel by viewModels<DetailsViewModel>()

    private var mJiankangmubiaoItemBean=JiankangmubiaoItemBean()/*详情内容*/


    @SuppressLint("SuspiciousIndentation")
    override fun initEvent() {
        setBarTitle("健康目标详情页")
        setBarColor("#FFFFFF","black")
        binding.apply{
            srv.setOnRefreshListener {
                loadData()
            }
            mIsBack.yes {
                crossOptButtonBtn0.isVisible = Utils.isAuthBack("jiankangmubiao","开始运动")
            }.otherwise {
                crossOptButtonBtn0.isVisible = Utils.isAuthFront("jiankangmubiao","开始运动")
            }
            crossOptButtonBtn0.setOnClickListener{/*跨表*/
                ARouter.getInstance().build(CommonArouteApi.PATH_ACTIVITY_ADDORUPDATE_YUNDONGJILU)
                    .withString("mCrossTable","jiankangmubiao")
                    .withObject("mCrossObj",mJiankangmubiaoItemBean)
                    .withString("mStatusColumnName","")
                    .withString("mStatusColumnValue","")
                    .withString("mTips","")
                    .navigation()
        }
            mIsBack.yes {
                crossOptButtonBtn1.isVisible = Utils.isAuthBack("jiankangmubiao","发送建议")
            }.otherwise {
                crossOptButtonBtn1.isVisible = Utils.isAuthFront("jiankangmubiao","发送建议")
            }
            crossOptButtonBtn1.setOnClickListener{/*跨表*/
                ARouter.getInstance().build(CommonArouteApi.PATH_ACTIVITY_ADDORUPDATE_GENZONGJIANYI)
                    .withString("mCrossTable","jiankangmubiao")
                    .withObject("mCrossObj",mJiankangmubiaoItemBean)
                    .withString("mStatusColumnName","")
                    .withString("mStatusColumnValue","")
                    .withString("mTips","")
                    .navigation()
        }
    }
    }

    override fun initData() {
        super.initData()
        showLoading()
        loadData()
        mDetailsViewModel.infoLiveData.observeKt(errorBlock = {binding.srv.isRefreshing =false}) {
            it.getOrNull()?.let { info->
                binding.srv.isRefreshing =false
                mJiankangmubiaoItemBean = info.data
                 binding.setInfo()
            }
        }
        mDetailsViewModel.updateLiveData.observeKt {
            it.getOrNull()?.let {
            }

        }
    }

    private fun loadData(){
        mDetailsViewModel.info("jiankangmubiao",mId.toString())
    }


    private fun JiankangmubiaocommonDetailsLayoutBinding.setInfo(){
        mubiaomingchengTv.text = "${mJiankangmubiaoItemBean.mubiaomingcheng}"
        zhidingshijianTv.text = "${mJiankangmubiaoItemBean.zhidingshijian}"
        duanlianmubiaoTv.text = "${mJiankangmubiaoItemBean.duanlianmubiao}"
        yundongmubiaoTv.text = "${mJiankangmubiaoItemBean.yundongmubiao}"
        yinshimubiaoTv.text = "${mJiankangmubiaoItemBean.yinshimubiao}"
        jihuakaishiTv.text = "${mJiankangmubiaoItemBean.jihuakaishi}"
        jihuajieshuTv.text = "${mJiankangmubiaoItemBean.jihuajieshu}"
        zhanghaoTv.text = "${mJiankangmubiaoItemBean.zhanghao}"
        xingmingTv.text = "${mJiankangmubiaoItemBean.xingming}"

    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== AppCompatActivity.RESULT_OK && requestCode==101){
            loadData()
        }
    }


}