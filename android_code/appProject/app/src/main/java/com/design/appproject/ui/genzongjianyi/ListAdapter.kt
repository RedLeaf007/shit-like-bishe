package com.design.appproject.ui.genzongjianyi
import com.union.union_basic.ext.otherwise
import com.union.union_basic.ext.yes
import android.widget.ImageView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.design.appproject.R
import com.design.appproject.bean.GenzongjianyiItemBean
import com.design.appproject.widget.LoadMoreAdapter
import com.design.appproject.ext.load
import com.design.appproject.utils.Utils

/**
 * 跟踪建议适配器列表
 */
class ListAdapter : LoadMoreAdapter<GenzongjianyiItemBean>(R.layout.genzongjianyi_list_item_layout) {

    var mIsBack = false/*是否后台进入*/
    override fun convert(holder: BaseViewHolder, item: GenzongjianyiItemBean) {
        holder.setText(R.id.xingming_tv,"姓名:"+ item.xingming.toString())
        holder.setText(R.id.yundongmubiao_tv,"运动目标:"+ item.yundongmubiao.toString())
        holder.setText(R.id.diaozhengmubiao_tv,"调整目标:"+ item.diaozhengmubiao.toString())
        mIsBack.yes {
            holder.setGone(R.id.edit_fl,!Utils.isAuthBack("genzongjianyi","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthBack("genzongjianyi","删除"))
        }.otherwise {
            holder.setGone(R.id.edit_fl,!Utils.isAuthFront("genzongjianyi","修改"))
            holder.setGone(R.id.delete_fl,!Utils.isAuthFront("genzongjianyi","删除"))
        }
    }
}