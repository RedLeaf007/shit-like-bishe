package com.design.appproject.bean.exampaper

data class ExamquestionItemBean (
    var id:Long=0L,
    var paperid:Long=0,
    var papername:String="",
    var questionname:String="",
    var options:String="",
    var score:Long=0,
    var answer:String="",
    var analysis:String="",
    var type:Long=0,
    var sequence:Long=0,
    var addtime:String?=null,
)

data class Options(
    var text:String ="",
    var code:String ="",
    var score:String ="",
)