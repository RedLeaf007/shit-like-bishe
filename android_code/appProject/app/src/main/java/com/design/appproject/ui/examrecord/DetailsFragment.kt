package com.design.appproject.ui.examrecord

import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.design.appproject.base.BaseBindingFragment
import com.design.appproject.base.CommonArouteApi
import com.design.appproject.databinding.BaseListLayoutBinding
import com.design.appproject.logic.viewmodel.examrecord.ExamRecordModel

/**
 * 试卷记录详情
 */
@Route(path = CommonArouteApi.PATH_FRAGMENT_DETAILS_EXAMRECORD)
class DetailsFragment: BaseBindingFragment<BaseListLayoutBinding>() {

    @JvmField
    @Autowired
    var mPaperId: Long = 0L

    @JvmField
    @Autowired
    var mUserId: Long = 0L

    private val mParams by lazy {
        mutableMapOf(
            "paperid" to mPaperId.toString(),
            "userid" to mUserId.toString(),
            "page" to "1",
            "limit" to "20"
        )
    }
    private val mExamRecordModel by viewModels<ExamRecordModel>()

    override fun initEvent() {
        binding.apply {
            setBarTitle("问卷记录")
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