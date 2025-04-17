package com.design.appproject.ui.examrecord

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.design.appproject.base.BaseBindingActivity
import com.design.appproject.base.BaseBindingFragment
import com.design.appproject.base.CommonArouteApi
import com.design.appproject.databinding.BaseListLayoutBinding
import com.design.appproject.logic.repository.HomeRepository
import com.design.appproject.utils.ArouterUtils

/**
 * 考试记录列表
 */
@Route(path = CommonArouteApi.PATH_FRAGMENT_LIST_EXAMRECORD)
class ListFragment: BaseBindingFragment<BaseListLayoutBinding>() {

    override fun initEvent() {
        binding.apply {
            setBarTitle("问卷记录")
            srv.isEnabled =false
            srv.setAdapter(ListAdapter().apply {
                setOnItemClickListener { adapter, view, position ->
                    ArouterUtils.startFragment(CommonArouteApi.PATH_FRAGMENT_DETAILS_EXAMRECORD,
                    mapOf("mPaperId" to data[position].paperid,"mUserId" to data[position].userid))
                }
            })
        }
    }

    override fun initData() {
        super.initData()
        HomeRepository.examRecordGroup().observeKt {
            it.getOrNull()?.let {
                binding.srv.setData(it.data.list,it.data.totalPage)
            }
        }
    }
}