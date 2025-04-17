package com.design.appproject.ui.examfailrecord

import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.design.appproject.base.BaseBindingFragment
import com.design.appproject.base.CommonArouteApi
import com.design.appproject.databinding.BaseListLayoutBinding
import com.design.appproject.logic.viewmodel.examrecord.ExamRecordModel
import com.design.appproject.ui.examrecord.RecordListAdapter
import com.design.appproject.utils.Utils

/**
 * 试卷错题记录
 */
@Route(path = CommonArouteApi.PATH_FRAGMENT_LIST_EXAMFAILRECORD)
class ListFragment: BaseBindingFragment<BaseListLayoutBinding>() {

    private val mParams by lazy {
        mutableMapOf(
            "sort" to "id",
            "userid" to Utils.getUserId().toString(),
            "page" to "1",
            "limit" to "20",
            "myscore" to "0"
        )
    }
    private val mExamRecordModel by viewModels<ExamRecordModel>()

    override fun initEvent() {
        binding.apply {
            setBarTitle("错题本")
            srv.setOnRefreshListener {
                mParams["page"] = "1"
                mExamRecordModel.examrecordList(mParams)
            }
            srv.setAdapter(RecordListAdapter().apply {
                pageLoadMoreListener {
                    mParams["page"] = it.toString()
                    mExamRecordModel.examrecordList(mParams)
                }
            })
        }
    }

    override fun initData() {
        super.initData()
        mExamRecordModel.examrecordList(mParams)
        mExamRecordModel.examrecordListLiveData.observeKt {
            it.getOrNull()?.let {
                binding.srv.setData(it.data.list,it.data.total)
            }
        }
    }
}