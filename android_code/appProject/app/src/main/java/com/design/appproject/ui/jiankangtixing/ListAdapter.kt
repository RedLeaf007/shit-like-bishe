package com.design.appproject.ui.jiankangtixing
import com.union.union_basic.ext.otherwise
import com.union.union_basic.ext.yes
import android.widget.ImageView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.design.appproject.R
import com.design.appproject.bean.JiankangtixingItemBean
import com.design.appproject.widget.LoadMoreAdapter
import com.design.appproject.ext.load
import com.design.appproject.utils.Utils

/**
 * 健康提醒适配器列表
 */
class ListAdapter : LoadMoreAdapter<JiankangtixingItemBean>(R.layout.jiankangtixing_list_item_layout) {

    var mIsBack = false/*是否后台进入*/
    override fun convert(holder: BaseViewHolder, item: JiankangtixingItemBean) {
        holder.setText(R.id.xingming_tv,"姓名:"+ item.xingming.toString())
        holder.setText(R.id.tixingshijian_tv,"提醒时间:"+ item.tixingshijian.toString())
        mIsBack.yes {
            holder.setGone(R.id.edit_fl,!Utils.isAuthBack("jiankangtixing","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthBack("jiankangtixing","删除"))
        }.otherwise {
            holder.setGone(R.id.edit_fl,!Utils.isAuthFront("jiankangtixing","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthFront("jiankangtixing","删除"))
        }
    }
}