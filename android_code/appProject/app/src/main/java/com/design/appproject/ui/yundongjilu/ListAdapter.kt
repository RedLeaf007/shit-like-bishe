package com.design.appproject.ui.yundongjilu
import com.union.union_basic.ext.otherwise
import com.union.union_basic.ext.yes
import android.widget.ImageView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.design.appproject.R
import com.design.appproject.bean.YundongjiluItemBean
import com.design.appproject.widget.LoadMoreAdapter
import com.design.appproject.ext.load
import com.design.appproject.utils.Utils

/**
 * 运动记录适配器列表
 */
class ListAdapter : LoadMoreAdapter<YundongjiluItemBean>(R.layout.yundongjilu_list_item_layout) {

    var mIsBack = false/*是否后台进入*/
    override fun convert(holder: BaseViewHolder, item: YundongjiluItemBean) {
        holder.setText(R.id.mubiaomingcheng_tv,"目标名称:"+ item.mubiaomingcheng.toString())
        holder.setText(R.id.yundongleixing_tv,"运动类型:"+ item.yundongleixing.toString())
        holder.setText(R.id.yundongshizhang_tv,"运动时长(m):"+ item.yundongshizhang.toString())
        mIsBack.yes {
            holder.setGone(R.id.edit_fl,!Utils.isAuthBack("yundongjilu","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthBack("yundongjilu","删除"))
        }.otherwise {
            holder.setGone(R.id.edit_fl,!Utils.isAuthFront("yundongjilu","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthFront("yundongjilu","删除"))
        }
    }
}