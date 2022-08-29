package com.deepblue.library.robotmsg.bean

/**
 * 福喜机器人专用推送指令
 * 推送报告
 * 命令类型:14005
 * 频率:状态改变的时候推送
 */
class FuxiStatus5 {

    /**
     * 推送实时故障信息
     * 命令类型:14005
     * 频率:状态改变的时候推送
     */
    var time: Long = 0
    var reason: String = ""
    var suggestion: String = ""
    var error_code: Int = 0

}