package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 查询运行信息
 */
class GetRunningInfoRes : Response() {

    init {
        json = RunningInfo()
    }

    fun getJson(): RunningInfo? {
        return JsonUtils.fromJson(json.toString(), RunningInfo::class.java)
    }

    class RunningInfo {
        var odom: Double = 0.0//本次累计运行距离,单位:m
        var total_odom: Double = 0.0//累计运行距离,单位:m
        var time: Double = 0.0//本次运行时间,单位:h
        var total_time: Double = 0.0//累计运行时间,单位:h
    }
}