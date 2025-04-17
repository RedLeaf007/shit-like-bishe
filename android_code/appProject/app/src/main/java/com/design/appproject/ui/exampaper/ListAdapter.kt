package com.design.appproject.ui.exampaper

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.design.appproject.R
import com.design.appproject.bean.exampaper.ExampaperItemBean
import com.design.appproject.widget.LoadMoreAdapter

/**
 * 试卷列表适配器
 */
class ListAdapter: LoadMoreAdapter<ExampaperItemBean>(R.layout.item_exampaper_list_layout) {

    override fun convert(holder: BaseViewHolder, item: ExampaperItemBean) {
        holder.setText(R.id.title_tv,item.name)
        holder.setText(R.id.time_tv,"问卷时长:"+item.time+"分钟")
    }
}