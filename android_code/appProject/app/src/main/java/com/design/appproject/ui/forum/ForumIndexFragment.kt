package com.design.appproject.ui.forum

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ColorUtils
import com.design.appproject.R
import com.design.appproject.base.BaseBindingFragment
import com.design.appproject.base.CommonArouteApi
import com.design.appproject.databinding.FragmentForumIndexLayoutBinding
import com.design.appproject.logic.repository.HomeRepository
import com.design.appproject.logic.viewmodel.forum.ForumModel
import com.lxj.xpopup.XPopup
import com.union.union_basic.ext.isNotNullOrEmpty
import com.union.union_basic.ext.otherwise
import com.union.union_basic.ext.showToast
import com.union.union_basic.ext.yes

/**
 * 论坛交流首页
 */
@Route(path = CommonArouteApi.PATH_FRAGMENT_LIST_FORUM)
class ForumIndexFragment: BaseBindingFragment<FragmentForumIndexLayoutBinding>() {
    @JvmField
    @Autowired
    var mHasBack: Boolean = true /*是否有返回按钮*/

    @JvmField
    @Autowired
    var mIsBack: Boolean = false /*是否用户后台进入*/

    @JvmField
    @Autowired
    var menuJump: String = "" /*14:我的发布*/

    private val mForumModel by viewModels<ForumModel>()

    override fun initEvent() {
        binding.apply {
            if (menuJump.isNotNullOrEmpty()){
                searchLl.isVisible =false
                titleTv.isVisible =false
                fragmentTitleBar.setRightText("新增")
                fragmentTitleBar.mRightTextView.setTextColor(ColorUtils.getColor(R.color.common_red))
                fragmentTitleBar.setOnRightTextViewClickListener {
                    ARouter.getInstance().build(CommonArouteApi.PATH_ACTIVITY_ADDORUPDATE_FORUM).navigation()
                }
                setBarTitle("我的发布",mHasBack)
            }else{
                setBarTitle("论坛交流",mHasBack)
            }
            srv.setOnRefreshListener {
                loadList(1)
            }
            srv.setAdapter(FroumIndexAdapter().apply {
                addChildClickViewIds(R.id.look_btn,R.id.update_btn,R.id.delete_btn)
                setOnItemChildClickListener { adapter, view, position ->
                    when(view.id){
                        R.id.delete_btn->{/*删除*/
                            XPopup.Builder(requireActivity()).asConfirm("提示","是否确认删除") {
                                HomeRepository.delete<Any>("forum",listOf(data[position].id)).observeKt {
                                    it.getOrNull()?.let {
                                        "删除成功".showToast()
                                        removeAt(position)
                                    }
                                }
                            }.show()
                        }
                        R.id.update_btn->{/*修改*/
                            ARouter.getInstance().build(CommonArouteApi.PATH_ACTIVITY_ADDORUPDATE_FORUM)
                                .withLong("mId",data[position].id).navigation()
                        }
                        R.id.look_btn->{/*查看*/
                            ARouter.getInstance().build(CommonArouteApi.PATH_ACTIVITY_DETAILS_FORUM)
                                .withLong("mId",data[position].id).navigation()
                        }
                    }
                }
                menuJump = this@ForumIndexFragment.menuJump
                setOnItemClickListener { adapter, view, position ->
                    ARouter.getInstance().build(CommonArouteApi.PATH_ACTIVITY_DETAILS_FORUM)
                        .withLong("mId",data[position].id).navigation()
                }
                pageLoadMoreListener {
                    loadList(it)
                }
            })
            searchBtn.setOnClickListener {
                loadList(1)
            }
        }
    }

    override fun initData() {
        super.initData()
        loadList(1)
        mForumModel.forumIndexListLiveData.observeKt {
            it.getOrNull()?.let {
                binding.srv.setData(it.data.list,it.data.total)
            }
        }
        mForumModel.myForumListLiveData.observeKt {
            it.getOrNull()?.let {
                binding.srv.setData(it.data.list,it.data.total)
            }
        }
    }

    private fun loadList(page:Int){
        mIsBack.yes {
            mForumModel.myForum(page,binding.searchValueEt.text.toString())
        }.otherwise {
            mForumModel.forumIndexList(page,binding.searchValueEt.text.toString())
        }
    }
}