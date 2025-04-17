package com.design.appproject.ui.jiankangmubiao
import com.union.union_basic.ext.otherwise
import com.union.union_basic.ext.yes
import android.widget.ImageView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.design.appproject.R
import com.design.appproject.bean.JiankangmubiaoItemBean
import com.design.appproject.widget.LoadMoreAdapter
import com.design.appproject.ext.load
import com.design.appproject.utils.Utils

/**
 * 健康目标适配器列表
 */
class ListAdapter : LoadMoreAdapter<JiankangmubiaoItemBean>(R.layout.jiankangmubiao_list_item_layout) {

    var mIsBack = false/*是否后台进入*/
    override fun convert(holder: BaseViewHolder, item: JiankangmubiaoItemBean) {
        holder.setText(R.id.mubiaomingcheng_tv,"目标名称:"+ item.mubiaomingcheng.toString())
        holder.setText(R.id.zhidingshijian_tv,"制定时间:"+ item.zhidingshijian.toString())
        holder.setText(R.id.xingming_tv,"姓名:"+ item.xingming.toString())
        mIsBack.yes {
            holder.setGone(R.id.edit_fl,!Utils.isAuthBack("jiankangmubiao","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthBack("jiankangmubiao","删除"))
        }.otherwise {
            holder.setGone(R.id.edit_fl,!Utils.isAuthFront("jiankangmubiao","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthFront("jiankangmubiao","删除"))
        }
    }
}