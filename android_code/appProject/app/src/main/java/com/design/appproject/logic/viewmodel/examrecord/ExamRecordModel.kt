package com.design.appproject.logic.viewmodel.examrecord

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.design.appproject.bean.examrecord.ExamrecordItemBean
import com.design.appproject.logic.repository.HomeRepository

/**
 * 试卷记录viewmodel
 */
class ExamRecordModel: ViewModel() {

    private val examrecordListData = MutableLiveData<Map<String, String>>()

    val examrecordListLiveData = Transformations.switchMap(examrecordListData) { request ->
        examrecordListData.value?.let {
            HomeRepository.list<ExamrecordItemBean>("examrecord", it)
        }
    }

    /**试卷记录列表*/
    fun examrecordList(params:Map<String, String>) {
        examrecordListData.value = params
    }
}