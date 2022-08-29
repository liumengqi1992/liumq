package com.deepblue.library.robotmsg.bean

/**
 * 福喜机器人专用推送指令
 * 推送报告
 * 命令类型:14003
 * 频率:1hz
 */
class FuxiStatus3 {

    /**
     * 推送任务进度
     * 命令类型:14003
     * 频率:1hz
     */
    var task_progress = TaskProgress()
    /**
     * 默认状态:3
     * 开始执行任务:60
     * 任务被取消:61
     * 任务被暂停:62
     * 任务继续:63
     * 任务完成:64
     * 任务失败:65
     */
    var task_state: Int = 0
    var finish_task_num: Int = 0//完成清扫任务的区域数量,可以根据该字段判断已经执行完的任务数

}