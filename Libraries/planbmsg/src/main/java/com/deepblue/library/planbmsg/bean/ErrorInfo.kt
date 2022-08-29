package com.deepblue.library.planbmsg.bean

import java.io.Serializable

/**
 * 实时故障状态(24002)
 */
class ErrorInfo : Serializable {

    //时间戳
    var time = 0L

    //原因
    var reason = ""

    //建议
    var suggestion = ""

    //故障代码
    var error_code = ""

    //故障等级
    var type = ""

    //RobotInfo.id(用于区分该故障信息属于对应的设备)
    var robotId = ""

    //本地数据库中的ID
    var id = -1

    //所属模块
    var belong = ""
}