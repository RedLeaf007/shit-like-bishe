package com.design.appproject.logic.viewmodel.forum

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.design.appproject.bean.forum.ForumItemBean
import com.design.appproject.logic.repository.HomeRepository
import com.design.appproject.logic.repository.UserRepository
import java.io.File

/**
 *论坛viewmodel
 */
class ForumModel: ViewModel() {

    private val forumIndexListData = MutableLiveData<List<Any?>>()

    val forumIndexListLiveData = Transformations.switchMap(forumIndexListData) { request ->
        forumIndexListData.value?.let {
            HomeRepository.forumIndex(it[0] as Int, it[1] as String?)
        }
    }

    /**论坛首页*/
    fun forumIndexList(page:Int,title:String?) {
        forumIndexListData.value = listOf(page,if (title.isNullOrEmpty()) null else "%$title%")
    }

    private val myForumListData = MutableLiveData<List<Any?>>()

    val myForumListLiveData = Transformations.switchMap(myForumListData) { request ->
        myForumListData.value?.let {
            HomeRepository.myForum(it[0] as Int, it[1] as String?)
        }
    }

    /**我的帖子*/
    fun myForum(page:Int,title:String?) {
        myForumListData.value = listOf(page,if (title.isNullOrEmpty()) null else "%$title%")
    }

    private val forumListData = MutableLiveData<Long>()

    val forumListLiveData = Transformations.switchMap(forumListData) { request ->
        forumListData.value?.let {
            HomeRepository.forumList(it)
        }
    }

    /**论坛详情*/
    fun forumList(id:Long) {
        forumListData.value = id
    }

    private val saveForumData = MutableLiveData<ForumItemBean>()

    val saveForumLiveData = Transformations.switchMap(saveForumData) { request ->
        saveForumData.value?.let {
            HomeRepository.save<ForumItemBean>("forum",it)
        }
    }

    /**新增帖子、评论*/
    fun saveForum(forumBean:ForumItemBean) {
        saveForumData.value = forumBean
    }

    private val updateForumData = MutableLiveData<ForumItemBean>()

    val updateForumLiveData = Transformations.switchMap(updateForumData) { request ->
        updateForumData.value?.let {
            UserRepository.update("forum",it)
        }
    }

    /**修改帖子、评论*/
    fun updateForum(forumBean:ForumItemBean) {
        updateForumData.value = forumBean
    }

    private val forumInfoData = MutableLiveData<Long>()

    val forumInfoLiveData = Transformations.switchMap(forumInfoData) { request ->
        forumInfoData.value?.let {
            HomeRepository.info<ForumItemBean>("forum",it)
        }
    }

    /**帖子详情*/
    fun forumInfo(id:Long) {
        forumInfoData.value = id
    }

    private val uploadData = MutableLiveData<File>()

    val uploadLiveData = Transformations.switchMap(uploadData) { request ->
        uploadData.value?.let {
            UserRepository.upload(it,"")
        }
    }

    /**上传文件*/
    fun upload(file: File) {
        uploadData.value = file
    }
}