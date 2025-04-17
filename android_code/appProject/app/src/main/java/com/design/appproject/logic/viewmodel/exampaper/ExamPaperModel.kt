package com.design.appproject.logic.viewmodel.exampaper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.design.appproject.bean.exampaper.ExampaperItemBean
import com.design.appproject.logic.repository.HomeRepository

/**
 * 试卷viewmodel
 */
class ExamPaperModel: ViewModel() {

    private val exampaperListData = MutableLiveData<Map<String, String>>()

    val exampaperListLiveData = Transformations.switchMap(exampaperListData) { request ->
        exampaperListData.value?.let {
            HomeRepository.list<ExampaperItemBean>("exampaper", it)
        }
    }

    /**试卷列表*/
    fun exampaperList(params:Map<String, String>) {
        exampaperListData.value = params
    }
}