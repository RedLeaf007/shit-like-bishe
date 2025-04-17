package com.design.appproject.ui.forum

import android.text.Html
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.design.appproject.R
import com.design.appproject.bean.forum.ForumItemBean
import com.design.appproject.ext.load
import com.design.appproject.widget.LoadMoreAdapter
import com.design.appproject.widget.CustomWebView

/**
 * 论坛评论适配器
 */
class ForumCommentAdapter:LoadMoreAdapter<ForumItemBean>(R.layout.item_forum_comment) {

    override fun convert(holder: BaseViewHolder, item: ForumItemBean) {
        holder.getView<ImageView>(R.id.avatar_qriv).load(context,item.avatarurl)
        holder.setText(R.id.name_tv,item.username)
        holder.setText(R.id.time_tv,item.addtime?:"")
        holder.getView<CustomWebView>(R.id.content_tv).setHtml(item.content)
        holder.getView<LinearLayout>(R.id.reply_ll).let {replyView-> /*二级回复*/
            replyView.removeAllViews()
            replyView.isVisible = !item.childs.isNullOrEmpty()
            item.childs?.forEach {
                replyView.addView(creatReplyView(it))
            }
        }
    }

    private fun creatReplyView(item:ForumItemBean) = LinearLayout.inflate(context,R.layout.item_forum_reply_layout,null).apply {
        findViewById<ImageView>(R.id.avatar_qriv).load(context,item.avatarurl)
        findViewById<TextView>(R.id.name_tv).text = item.username
        findViewById<TextView>(R.id.time_tv).text = item.addtime?:""
        findViewById<CustomWebView>(R.id.content_tv).setHtml(item.content)
    }
}