package com.deepblue.library.robotmsg.bean

/**
 * 福喜机器人专用推送指令
 * 推送报告
 * 命令类型:14004
 * 频率:状态改变的时候推送
 */
class FuxiStatus4 {

    /**
     * 推送急停按钮状态
     * 命令类型:14004
     * 频率:状态改变的时候推送
     */
    var stop_button: Boolean = false//true:按下, false:未按下

}