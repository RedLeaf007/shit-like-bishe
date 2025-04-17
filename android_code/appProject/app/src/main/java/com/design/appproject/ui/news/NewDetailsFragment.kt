package com.design.appproject.ui.news
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ColorUtils
import com.design.appproject.R
import com.design.appproject.base.BaseBindingFragment
import com.design.appproject.base.CommonArouteApi
import com.design.appproject.bean.StoreupItemBean
import com.design.appproject.bean.news.NewsItemBean
import com.design.appproject.databinding.NewsDetailsActivityBinding
import com.design.appproject.logic.repository.HomeRepository
import com.design.appproject.logic.repository.UserRepository
import com.design.appproject.logic.viewmodel.news.NewDetailsModel
import com.design.appproject.utils.Utils
import com.union.union_basic.ext.drawableImg
import com.union.union_basic.ext.showToast
@Route(path = CommonArouteApi.PATH_FRAGMENT_DETAILS_NEWS)
class NewDetailsFragment: BaseBindingFragment<NewsDetailsActivityBinding>() {

    private var mNewsBean:NewsItemBean=NewsItemBean()
    @JvmField
    @Autowired
    var mId: Long = 0 /*id*/

    private val mNewDetailsModel by viewModels<NewDetailsModel>()
    override fun initEvent() {
        setBarTitle("健康资讯")
    }

    override fun initData() {
        super.initData()
        mNewDetailsModel.newsInfo(mId)
        mNewDetailsModel.newsInfoLiveData.observeKt {
            it.getOrNull()?.let {
                mNewsBean=it.data
                binding.headerView.titleTv.text = it.data.title
                binding.headerView.introductionTv.text = it.data.introduction
                binding.headerView.webview.setHtml(it.data.content.replace("<img","<img style=\"width: 100%;").trim())

                binding.collectionTv.text = it.data.storeupNumber.toString()
                binding.likeTv.text = it.data.thumbsupNumber.toString()
            }
        }
        HomeRepository.list<StoreupItemBean>("storeup", mapOf("page" to "1","limit" to "1","refid" to mId.toString(),
            "tablename" to "news","userid" to Utils.getUserId().toString(),"type" to "1")).observeKt {
            it.getOrNull()?.let {
                binding.collectionTv.isSelected = it.data.list.isNotEmpty()/*true为已收藏*/
                val color = ColorUtils.getColor(if (binding.collectionTv.isSelected) R.color.common_yellow_color else R.color.common_title_color)
                binding.collectionTv.drawableImg(R.mipmap.icon_collection,0,color)
                binding.collectionTv.setTextColor(color)
            }
        }

        binding.collectionTv.setOnClickListener {
            if (binding.collectionTv.isSelected){/*取消收藏或关注*/
                HomeRepository.list<StoreupItemBean>("storeup", mapOf("page" to "1","limit" to "1",
                    "refid" to mId.toString(), "tablename" to "news", "userid" to Utils.getUserId().toString(), "type" to "1" )).observeKt {
                    it.getOrNull()?.let {
                        if (it.data.list.isNotEmpty()){
                            HomeRepository.delete<StoreupItemBean>("storeup", listOf(it.data.list[0].id)).observeKt {
                                it.getOrNull()?.let {
                                    mNewsBean.storeupNumber-=1
                                    UserRepository.update("news",mNewsBean).observeKt {
                                        it.getOrNull()?.let {
                                            binding.collectionTv.text = mNewsBean.storeupNumber.toString()
                                        }
                                    }
                                    "取消成功".showToast()
                                    binding.collectionTv.isSelected =false
                                    val color = ColorUtils.getColor(if (binding.collectionTv.isSelected) R.color.common_yellow_color else R.color.common_title_color)
                                    binding.collectionTv.drawableImg(R.mipmap.icon_collection,0,color)
                                    binding.collectionTv.setTextColor(color)
                                }
                            }
                        }
                    }
                }
            }else{/*收藏或关注*/
                HomeRepository.add<StoreupItemBean>("storeup",StoreupItemBean(
                    userid = Utils.getUserId(),
                    name = mNewsBean.title,
                    refid = mNewsBean.id,
                    tablename="news",
                    type="1")).observeKt {
                    it.getOrNull()?.let {
                        mNewsBean.storeupNumber+=1
                        UserRepository.update("news",mNewsBean).observeKt {
                            it.getOrNull()?.let {
                                binding.collectionTv.text = mNewsBean.storeupNumber.toString()
                            }
                        }
                        "收藏成功".showToast()
                        binding.collectionTv.isSelected = true
                        val color = ColorUtils.getColor(if (binding.collectionTv.isSelected) R.color.common_yellow_color else R.color.common_title_color)
                        binding.collectionTv.drawableImg(R.mipmap.icon_collection,0,color)
                        binding.collectionTv.setTextColor(color)
                    }
                }
            }
        }
        HomeRepository.list<StoreupItemBean>("storeup", mapOf("page" to "1","limit" to "1","refid" to mId.toString(),
            "tablename" to "news","userid" to Utils.getUserId().toString(),"type" to "21")).observeKt {
            it.getOrNull()?.let {
                binding.likeTv.isSelected = it.data.list.isNotEmpty()/*true为已收藏*/
                val color = ColorUtils.getColor(if (binding.likeTv.isSelected) R.color.common_yellow_color else R.color.common_title_color)
                binding.likeTv.drawableImg(R.mipmap.icon_like,0,color)
                binding.likeTv.setTextColor(color)
            }
        }

        binding.likeTv.setOnClickListener {
            if (binding.likeTv.isSelected){
                HomeRepository.list<StoreupItemBean>("storeup", mapOf("page" to "1","limit" to "1",
                    "refid" to mId.toString(), "tablename" to "news", "userid" to Utils.getUserId().toString(), "type" to "21" )).observeKt {
                    it.getOrNull()?.let {
                        if (it.data.list.isNotEmpty()){
                            HomeRepository.delete<StoreupItemBean>("storeup", listOf(it.data.list[0].id)).observeKt {
                                it.getOrNull()?.let {
                                    mNewsBean.thumbsupNumber-=1
                                    UserRepository.update("news",mNewsBean).observeKt {
                                        it.getOrNull()?.let {
                                            binding.likeTv.text = mNewsBean.thumbsupNumber.toString()
                                        }
                                    }
                                    "取消成功".showToast()
                                    binding.likeTv.isSelected =false
                                    val color = ColorUtils.getColor(if (binding.likeTv.isSelected) R.color.common_yellow_color else R.color.common_title_color)
                                    binding.likeTv.drawableImg(R.mipmap.icon_like,0,color)
                                    binding.likeTv.setTextColor(color)
                                }
                            }
                        }
                    }
                }
            }else{
                HomeRepository.add<StoreupItemBean>("storeup",StoreupItemBean(
                    userid = Utils.getUserId(),
                    name = mNewsBean.title,
                    refid = mNewsBean.id,
                    tablename="news",
                    type="21")).observeKt {
                    it.getOrNull()?.let {
                        mNewsBean.thumbsupNumber+=1
                        UserRepository.update("news",mNewsBean).observeKt {
                            it.getOrNull()?.let {
                                binding.likeTv.text = mNewsBean.thumbsupNumber.toString()
                            }
                        }
                        "点赞成功".showToast()
                        binding.likeTv.isSelected = true
                        val color = ColorUtils.getColor(if (binding.likeTv.isSelected) R.color.common_yellow_color else R.color.common_title_color)
                        binding.likeTv.drawableImg(R.mipmap.icon_like,0,color)
                        binding.likeTv.setTextColor(color)
                    }
                }
            }
        }
    }
}