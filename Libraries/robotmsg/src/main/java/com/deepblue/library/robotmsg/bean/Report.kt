package com.deepblue.library.robotmsg.bean

class Report {
    var device_name: String = ""
    var user_name: String = ""
    var task_chain_name: String = ""
    var map_name: String = ""
    var work_mode: String = ""//auto/manual
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
    var start_time: Long = 0//自纪元 Epoch(1970-01-01 00:00:00 UTC)起经过的时间,以秒为单位
    var end_time: Long = 0//自纪元 Epoch(1970-01-01 00:00:00 UTC)起经过的时间,以秒为单位
    var total_time: Long = 0//sec
    var task_area: Double = 0.0//m^2
    var clean_area: Double = 0.0//m^2, 总共清扫面积
}