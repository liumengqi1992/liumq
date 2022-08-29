package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 获取历史报告
 */
class GetHistoryReportRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        var reports = ArrayList<Report>()
    }

    class Report {
        var device_name: String = ""
        var user_name: String = ""
        var task_chain_name: String = ""
        var map_name: String = ""
        var work_mode: String = ""
        var task_state: Int = 0// 64:完成;65:失败;61:被取消
        var finish_task_num: Int = 0//完成清扫任务的区域数量,可以根据该字段判断已经执行完的任务数
        var start_time: Long = 0//自纪元 Epoch(1970-01-01 00:00:00 UTC)起经过的时间,以秒为单位
        var end_time: Long = 0//自纪元 Epoch(1970-01-01 00:00:00 UTC)起经过的时间,以秒为单位
        var total_time: Int = 0//sec
        var task_area: Int = 0//m^2
        var clean_area: Int = 0//m^2
    }
}