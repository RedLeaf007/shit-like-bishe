package com.design.appproject.bean.news

data class NewsItemBean(
    var id:Long=0L,
    var title:String="",
    var introduction:String="",
    var picture:String="",
    var content:String="",
    var thumbsupNumber:Int=0,
    var crazilyNumber:Int=0,
    var storeupNumber:Int=0,
    var addtime:String?=null,
)