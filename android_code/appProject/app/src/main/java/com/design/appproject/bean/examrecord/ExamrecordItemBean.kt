package com.design.appproject.bean.examrecord

data class ExamrecordItemBean (
    var id:Long=0L,
    var username:String="",
    var paperid:Long=0,
    var papername:String="",
    var questionid:Long=0,
    var questionname:String="",
    var type:Long=0,
    var ismark:Long=0,
    var options:String="",
    var score:Long=0,
    var answer:String="",
    var analysis:String="",
    var myscore:Long=0,
    var myanswer:String="",
    var userid:Long=0,
    var addtime:String?=null,
)