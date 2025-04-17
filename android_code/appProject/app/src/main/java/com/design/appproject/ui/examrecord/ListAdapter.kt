package com.design.appproject.ui.examrecord

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.design.appproject.R
import com.design.appproject.bean.examrecord.ExamrecordItemBean
import com.design.appproject.widget.LoadMoreAdapter

/**
 * 考试记录适配器
 */
class ListAdapter: LoadMoreAdapter<ExamrecordItemBean>(R.layout.examrecord_list_item_layout) {

    override fun convert(holder: BaseViewHolder, item: ExamrecordItemBean) {
        holder.setText(R.id.title_tv,"健康小测名称：${item.papername}  (${item.username})")
        holder.setText(R.id.score_tv,"分数：${item.myscore}")
    }
}