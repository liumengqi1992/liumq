package com.deepblue.library.planbmsg.bean

/**
 *
 * task_status   1-未开始
                2-正在执行
                3-任务异常
                4-完成任务
                5-取消任务
                7-暂停任务
                8-继续任务
                9-开始任务
                10-中断任务
                11-删除任务

clean_mode      1-标准模模式
                2-干吸模式
                3-节水模式
                4-深度模式
                5-导航消毒模式
                6-标准消毒模式
                7-干吸消毒模式
                8-节水消毒模式
                9-深度消毒模式
 */
class TaskReport {
    var name: String? = null
    var model: String? = null
    var id: String? = null
    var operator: String? = null
    var map_id: Int? = null
    var task_name: String? = null
    var task_id: Int? = null
    var task_status: Int? = null
    var start_time: String? = null
    var end_time: String? = null
    var cost_time: Int? = null
    var task_area: Int? = null
    var clean_area: Int? = null
    var percent: Int? = null
    var use_water: Double? = null
    var total_times: Int? = null
    var finish_times: Int? = null
    var clean_mode: Int? = null
    var distance: Int? = null
    var walk_path: String? = null
    var message: String? = null
    var error_code: String? = null
}