package com.design.appproject.ui.forum

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.design.appproject.R
import com.design.appproject.bean.forum.ForumItemBean
import com.design.appproject.widget.LoadMoreAdapter
import com.union.union_basic.ext.isNotNullOrEmpty

/**
 * 论坛首页index适配器
 */
class FroumIndexAdapter:LoadMoreAdapter<ForumItemBean>(R.layout.item_forum_index) {

    var menuJump = ""

    override fun convert(holder: BaseViewHolder, item: ForumItemBean) {
        holder.setGone(R.id.options_llc,menuJump.isNullOrEmpty())
        holder.setText(R.id.title_tv,item.title)
        holder.setText(R.id.user_tv,"发布人：${item.username}")
        holder.setText(R.id.time_tv,"发布时间：${item.addtime}")
    }
}