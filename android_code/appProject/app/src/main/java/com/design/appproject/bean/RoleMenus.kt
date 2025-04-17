package com.design.appproject.bean

import com.blankj.utilcode.util.GsonUtils
import com.google.gson.reflect.TypeToken

var roleMenusList =
    GsonUtils.fromJson<List<RoleMenusItem>>("[{\"backMenu\":[{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-goodsnew\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"classname\":\"yonghu\",\"menu\":\"用户\",\"menuJump\":\"列表\",\"tableName\":\"yonghu\"}],\"fontClass\":\"icon-user1\",\"menu\":\"用户管理\",\"unicode\":\"&#xef97;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"开始运动\",\"发送建议\"],\"appFrontIcon\":\"cuIcon-goodsnew\",\"buttons\":[\"查看\",\"修改\",\"删除\",\"发送建议\"],\"classname\":\"jiankangmubiao\",\"menu\":\"健康目标\",\"menuJump\":\"列表\",\"tableName\":\"jiankangmubiao\"}],\"fontClass\":\"icon-common34\",\"menu\":\"健康目标管理\",\"unicode\":\"&#xee85;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-circle\",\"buttons\":[\"查看\",\"修改\",\"删除\"],\"classname\":\"yundongjilu\",\"menu\":\"运动记录\",\"menuJump\":\"列表\",\"tableName\":\"yundongjilu\"}],\"fontClass\":\"icon-common43\",\"menu\":\"运动记录管理\",\"unicode\":\"&#xef27;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-pay\",\"buttons\":[\"查看\",\"修改\",\"删除\"],\"classname\":\"genzongjianyi\",\"menu\":\"跟踪建议\",\"menuJump\":\"列表\",\"tableName\":\"genzongjianyi\"}],\"fontClass\":\"icon-common39\",\"menu\":\"跟踪建议管理\",\"unicode\":\"&#xeeba;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-goods\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"classname\":\"jiankangtixing\",\"menu\":\"健康提醒\",\"menuJump\":\"列表\",\"tableName\":\"jiankangtixing\"}],\"fontClass\":\"icon-common3\",\"menu\":\"健康提醒管理\",\"unicode\":\"&#xeda5;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-keyboard\",\"buttons\":[\"查看\",\"修改\",\"删除\",\"查看评论\"],\"classname\":\"forum\",\"menu\":\"论坛交流\",\"tableName\":\"forum\"}],\"fontClass\":\"icon-common38\",\"menu\":\"论坛管理\",\"unicode\":\"&#xeeb2;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"组卷\",\"调查统计\"],\"appFrontIcon\":\"cuIcon-time\",\"buttons\":[\"查看\",\"调查统计\"],\"classname\":\"exampaper\",\"menu\":\"健康小测\",\"tableName\":\"exampaper\"},{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"打印\",\"导出\"],\"appFrontIcon\":\"cuIcon-time\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"打印\"],\"classname\":\"examquestion\",\"menu\":\"试题管理\",\"menuJump\":\"列表\",\"tableName\":\"examquestion\"},{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"打印\",\"导出\"],\"appFrontIcon\":\"cuIcon-full\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"classname\":\"examquestionbank\",\"menu\":\"试题库管理\",\"menuJump\":\"列表\",\"tableName\":\"examquestionbank\"},{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"批卷\"],\"appFrontIcon\":\"cuIcon-keyboard\",\"buttons\":[\"查看\"],\"classname\":\"examrecord\",\"menu\":\"健康小测\",\"tableName\":\"examrecord\"}],\"fontClass\":\"icon-common22\",\"menu\":\"试卷管理\",\"unicode\":\"&#xee04;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-camera\",\"buttons\":[\"查看\",\"修改\"],\"classname\":\"config\",\"menu\":\"轮播图\",\"menuJump\":\"列表\",\"tableName\":\"config\"},{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-brand\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"classname\":\"news\",\"menu\":\"健康资讯\",\"menuJump\":\"列表\",\"tableName\":\"news\"}],\"fontClass\":\"icon-common13\",\"menu\":\"系统管理\",\"unicode\":\"&#xedf7;\"}],\"frontMenu\":[{\"child\":[{\"appFrontIcon\":\"cuIcon-shop\",\"buttons\":[\"新增\"],\"classname\":\"forum\",\"menu\":\"论坛交流\",\"menuJump\":\"列表\",\"tableName\":\"forum\"}],\"menu\":\"论坛交流\"}],\"hasBackLogin\":\"是\",\"hasBackRegister\":\"否\",\"hasFrontLogin\":\"否\",\"hasFrontRegister\":\"否\",\"pathName\":\"users\",\"roleName\":\"管理员\",\"tableName\":\"users\"},{\"backMenu\":[{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"开始运动\",\"发送建议\"],\"appFrontIcon\":\"cuIcon-goodsnew\",\"buttons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"开始运动\"],\"classname\":\"jiankangmubiao\",\"menu\":\"健康目标\",\"menuJump\":\"列表\",\"tableName\":\"jiankangmubiao\"}],\"fontClass\":\"icon-common34\",\"menu\":\"健康目标管理\",\"unicode\":\"&#xee85;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-circle\",\"buttons\":[\"查看\",\"修改\"],\"classname\":\"yundongjilu\",\"menu\":\"运动记录\",\"menuJump\":\"列表\",\"tableName\":\"yundongjilu\"}],\"fontClass\":\"icon-common43\",\"menu\":\"运动记录管理\",\"unicode\":\"&#xef27;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-pay\",\"buttons\":[\"查看\"],\"classname\":\"genzongjianyi\",\"menu\":\"跟踪建议\",\"menuJump\":\"列表\",\"tableName\":\"genzongjianyi\"}],\"fontClass\":\"icon-common39\",\"menu\":\"跟踪建议管理\",\"unicode\":\"&#xeeba;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\"],\"appFrontIcon\":\"cuIcon-goods\",\"buttons\":[\"查看\"],\"classname\":\"jiankangtixing\",\"menu\":\"健康提醒\",\"menuJump\":\"列表\",\"tableName\":\"jiankangtixing\"}],\"fontClass\":\"icon-common3\",\"menu\":\"健康提醒管理\",\"unicode\":\"&#xeda5;\"},{\"child\":[{\"allButtons\":[\"新增\",\"查看\",\"修改\",\"删除\",\"批卷\"],\"appFrontIcon\":\"cuIcon-keyboard\",\"buttons\":[\"查看\"],\"classname\":\"examrecord\",\"menu\":\"健康小测\",\"tableName\":\"examrecord\"}],\"fontClass\":\"icon-common22\",\"menu\":\"试卷管理\",\"unicode\":\"&#xee04;\"}],\"frontMenu\":[{\"child\":[{\"appFrontIcon\":\"cuIcon-shop\",\"buttons\":[\"新增\"],\"classname\":\"forum\",\"menu\":\"论坛交流\",\"menuJump\":\"列表\",\"tableName\":\"forum\"}],\"menu\":\"论坛交流\"}],\"hasBackLogin\":\"否\",\"hasBackRegister\":\"否\",\"hasFrontLogin\":\"是\",\"hasFrontRegister\":\"是\",\"pathName\":\"yonghu\",\"roleName\":\"用户\",\"tableName\":\"yonghu\"}]", object : TypeToken<List<RoleMenusItem>>() {}.type)

data class RoleMenusItem(
    val backMenu: List<MenuBean>,
    val frontMenu: List<MenuBean>,
    val hasBackLogin: String,
    val hasBackRegister: String,
    val hasFrontLogin: String,
    val hasFrontRegister: String,
    val roleName: String,
    val tableName: String
)

data class MenuBean(
    val child: List<Child>,
    val menu: String,
    val fontClass: String,
    val unicode: String=""
)

data class Child(
    val appFrontIcon: String,
    val buttons: List<String>,
    val menu: String,
    val menuJump: String,
    val tableName: String
)

