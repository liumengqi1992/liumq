package com.deepblue.library.robotmsg.bean

/**
 * 导航平台专用推送指令
 * 推送状态
 * 频率:在状态改变的时候进行推送
 * 命令类型:14000
 */
class NavigationStatus {

    /**
     * 默认状态:3
     * 开始重定位:10
     * 完成重定位:11
     * 重定位失败:12
     */
    var reloc_status: Int? = null//重定位状态

    /**
     * 默认状态:3
     * 充电已连接:20
     * 充电已断开:21
     */
    var charge_status: Int? = null//充电状态

    /**
     * 默认状态:3
     * 开始 slam:40
     * 结束 slam:41
     * slam 失败:42
     */
    var slam_map_status: Int? = null//创建地图状态

    var task: TaskStatus? = null//任务状态

    /**
     * 急停被按下:1
     * 急停没有按下:0
     */
    var emergency_status: Int? = null//急停按钮状态

    var error_code: ArrayList<String>? = null//故障状态

    /**
     * 参数未设置或设置错误:0
     * 参数设置完成:1
     */
    var set_params_status: Int? = null//设置参数状态

    /**
     * 默认状态:3
     * 开始创建任务:80
     * 创建任务完成:81
     * 创建任务失败:82
     */
    var create_task_status: Int? = null//创建任务状态

    class TaskStatus {
        var map: String = ""
        var task: String = ""
        /**
         * 默认状态:3
         * 开始执行任务:60
         * 任务被取消:61
         * 任务被暂停:62
         * 任务继续:63
         * 任务完成:64
         * 任务失败:65
         */
        var status: Int = 0
        /**
         * 当前已经完成的步骤数目
         */
        var step: Int = 0
        /**
         * 任务总的步骤数目
         */
        var total_steps: Int = 0
    }
}