package com.design.appproject.bean.forum

data class ForumItemBean (
    var id:Long=0L,
    var childs:List<ForumItemBean>? = null,
    var title:String="",
    var content:String="",
    var parentid:Long=0,
    var userid:Long=0,
    var username:String="",
    var avatarurl:String="",
    var isdone:String="",
    var isTop:Int=0,
    var topTime:String="",
    var addtime:String?=null,
)