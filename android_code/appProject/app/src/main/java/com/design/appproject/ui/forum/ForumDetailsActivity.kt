package com.design.appproject.ui.forum
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.design.appproject.R
import com.design.appproject.base.BaseBindingActivity
import com.design.appproject.base.CommonArouteApi
import com.design.appproject.base.CommonBean
import com.design.appproject.bean.forum.ForumItemBean
import com.design.appproject.databinding.ActivityForumDetailsLayoutBinding
import com.design.appproject.logic.viewmodel.forum.ForumModel
import com.design.appproject.widget.CustomWebView
import com.lxj.xpopup.XPopup
import com.union.union_basic.ext.showToast
import com.union.union_basic.utils.StorageUtil
import com.design.appproject.utils.Utils
/**
 * 论坛详情界面
 */
@Route(path = CommonArouteApi.PATH_ACTIVITY_DETAILS_FORUM)
class ForumDetailsActivity: BaseBindingActivity<ActivityForumDetailsLayoutBinding>() {

    @JvmField
    @Autowired
    var mId: Long = 0 /*id*/

    private val mForumModel by viewModels<ForumModel>()

    private var mForumItemBean:ForumItemBean=ForumItemBean()


    private val mHeaderView by lazy {
        LayoutInflater.from(this).inflate(R.layout.header_forum_details,null)
    }

    private val mCommentDialog by lazy {
        CommentDialog(this){content,pid ->
            mForumModel.saveForum(ForumItemBean(
                parentid = pid,
                content = content,
                avatarurl = StorageUtil.decodeString(CommonBean.HEAD_URL_KEY)?:"",
                username = StorageUtil.decodeString(CommonBean.USERNAME_KEY)?:"",
            ))
        }
    }
    override fun initEvent() {
        setBarTitle("论坛交流发帖详情")
        binding.apply {
            commentSrv.setOnRefreshListener {
                mForumModel.forumList(mId)
            }
            commentSrv.setAdapter(ForumCommentAdapter().apply {
                setOnItemClickListener { adapter, view, position ->
                    XPopup.Builder(this@ForumDetailsActivity).asCustom(mCommentDialog.apply {
                        mPid = data[position].id
                        mReplyUserName = data[position].username
                    }).show()
                }
                addHeaderView(mHeaderView)
            })

            commentTv.setOnClickListener {/*新增评论*/
                XPopup.Builder(this@ForumDetailsActivity).asCustom(mCommentDialog.apply {
                    mPid = mId
                    mReplyUserName=""
                }).show()
            }
        }
    }

    override fun initData() {
        super.initData()
        mForumModel.forumList(mId)
        mForumModel.forumListLiveData.observeKt {
            it.getOrNull()?.let {
                mHeaderView.findViewById<TextView>(R.id.forum_title_tv).text = it.data.title
                mHeaderView.findViewById<TextView>(R.id.user_tv).text = "发布人：${it.data.username}"
                mHeaderView.findViewById<CustomWebView>(R.id.forum_content_cwv).setHtml(it.data.content)
                mForumItemBean = it.data
                binding.commentSrv.setData(it.data.childs,it.data.childs?.size?:0)
            }
        }

        mForumModel.saveForumLiveData.observeKt {
            it.getOrNull()?.let {
                mForumModel.forumList(mId)
                "回复成功".showToast()
                mCommentDialog.cancleAndDismiss()
            }
        }
    }
}