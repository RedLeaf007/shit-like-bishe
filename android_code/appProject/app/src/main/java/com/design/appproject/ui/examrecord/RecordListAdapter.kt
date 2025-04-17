package com.design.appproject.ui.examrecord

import android.widget.TextView
import com.blankj.utilcode.util.ColorUtils
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.design.appproject.R
import com.design.appproject.bean.examrecord.ExamrecordItemBean
import com.design.appproject.widget.LoadMoreAdapter

/**
 * 考试记录适配器
 */
class RecordListAdapter: LoadMoreAdapter<ExamrecordItemBean>(R.layout.examrecord_details_item_layout) {

    override fun convert(holder: BaseViewHolder, item: ExamrecordItemBean) {
        holder.setText(R.id.title_tv,"健康小测：${item.papername}")
        holder.setText(R.id.question_tv,"试题库：${item.questionname}")
        holder.setText(R.id.answer_tv,"答案：${item.answer}")
        holder.setText(R.id.my_answer_tv,"我的答案：${item.myanswer}")
        holder.setText(R.id.score_tv,"分数：${item.score}")
        holder.getView<TextView>(R.id.my_score_tv).let {
            if (item.myscore>0){
                it.setBackgroundColor(ColorUtils.getColor(R.color.common_red))
            }else{
                it.setBackgroundColor(ColorUtils.getColor(R.color.common_bg_color_gray2))
            }
            it.text = "得分：${item.myscore}"
        }
    }
}