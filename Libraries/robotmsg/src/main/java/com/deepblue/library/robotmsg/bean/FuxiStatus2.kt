package com.deepblue.library.robotmsg.bean

/**
 * 福喜机器人专用推送指令
 * 推送报告
 * 命令类型:14002
 * 频率:只在设备启动的时候推送一次
 */
class FuxiStatus2 {

    /**
     * 推送初始化信息
     * 命令类型:14002
     * 频率:只在设备启动的时候推送一次
     */
    var system_time: Long = 0
    var init_info = ArrayList<InitInfo>()

}