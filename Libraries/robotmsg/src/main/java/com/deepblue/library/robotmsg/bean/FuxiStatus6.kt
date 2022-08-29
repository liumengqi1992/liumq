package com.deepblue.library.robotmsg.bean

/**
 * 福喜机器人专用推送指令
 * 推送报告
 * 命令类型:14006
 * 频率:状态改变的时候推送
 */
class FuxiStatus6 {

    /**
     * 推送电量信息
     * 命令类型:14006
     * 频率:状态改变的时候推送
     */
    var power: Int = 0//0:低电量(低于百分之 10)提醒,1:高电量(高于百分之 80)提醒

}