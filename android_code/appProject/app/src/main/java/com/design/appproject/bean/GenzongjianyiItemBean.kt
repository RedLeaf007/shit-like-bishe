package com.design.appproject.bean

/**
 * 跟踪建议实体类
 */
data class GenzongjianyiItemBean(
    var id:Long=0L,
    var zhanghao:String="",
    var xingming:String="",
    var yundongmubiao:String="",
    var diaozhengmubiao:String="",
    var diaozhengjianyi:String="",
    var gengxinshijian:String="",
    var addtime:String?=null,
)