package com.design.appproject.ui.yonghu

import com.design.appproject.databinding.YonghuFilterDialogLayoutBinding
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.dylanc.viewbinding.inflate
import com.design.appproject.R
import com.design.appproject.logic.repository.UserRepository
import com.lxj.xpopup.core.DrawerPopupView
import com.lxj.xpopup.util.XPopupUtils
import com.union.union_basic.ext.toConversion
import android.app.Activity
import com.blankj.utilcode.util.ScreenUtils
import com.union.union_basic.ext.isNotNullOrEmpty
import com.union.union_basic.ext.yes

class ListFilterDialog(context: Context) : DrawerPopupView(context) {

    lateinit var binding: YonghuFilterDialogLayoutBinding

    override fun addInnerContent() {
        binding = drawerContentContainer.inflate()
    }

    private val params by lazy { /*请求参数*/
        mutableMapOf<String,String>()
    }
    var callBackListener: ((isEnsure:Boolean,params:MutableMap<String,String>?) -> Unit)? =null

    override fun onCreate() {
        super.onCreate()
        binding.apply {
            dialogNsv.layoutParams = dialogNsv.layoutParams.apply {
                width = ScreenUtils.getScreenWidth()
                height = ScreenUtils.getScreenHeight()
            }
            dialogFl.setOnClickListener { dismiss() }
            boxMll.setOnClickListener {  }
            "男,女".split(",").forEachIndexed {index, s ->
                xingbieFbl.addView(createCustomItemView(index,s,xingbieFbl))
            }
            resetBtn.setOnClickListener { //重置
                xingmingEt.setText("")
                xingbieFbl.children.forEach { it.isSelected=false }
                callBackListener?.invoke(false,null)
            }
            ensureBtn.setOnClickListener { //确定
                params.clear()
                xingmingEt.text.toString().isNotNullOrEmpty().yes {
                    val filterContent = xingmingEt.text.toString()
                    params.put("xingming","%${filterContent}%")
                }
                val xingbieString = xingbieFbl.children.filter { it.isSelected }.map { it.toConversion<TextView>()?.text.toString() }.joinToString()
                xingbieString.isNotNullOrEmpty().yes {
                    params.put("xingbie",xingbieString)
                }
                callBackListener?.invoke(true,params)
                dismiss()
            }
        }
    }

    private fun createCustomItemView(index:Int,title:String,viewGroup:ViewGroup,isSelect:Boolean=false):View= LayoutInflater.from(context).inflate(if (isSelect) R.layout.item_custom_active_layout else R.layout.item_custom_layout, viewGroup, false).apply{
        toConversion<TextView>()?.text = title
        isSelected=isSelect
        setOnClickListener {
            if (isSelected){
                return@setOnClickListener
            }else{
                val oldIndex= viewGroup.children.indexOfFirst { it.isSelected }
                if (oldIndex>=0){
                    val itemView = viewGroup.children.find { it.isSelected }
                    val oldTitle = itemView?.toConversion<TextView>()?.text.toString()?:""
                    viewGroup.removeViewAt(oldIndex)
                    val view = createCustomItemView(oldIndex,oldTitle,viewGroup)
                    viewGroup.addView(view,oldIndex)
                }
                viewGroup.removeViewAt(index)
                viewGroup.addView(createCustomItemView(index,title,viewGroup,true),index)
            }
        }
    }
}