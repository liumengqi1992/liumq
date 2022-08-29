package com.deepblue.cloudmsg.cleanapp

import com.deepblue.cloudmsg.Request

/**
 * 查询故障错误列表
 */
class QueryBreakdownsReq(val deviceCode: String, val page: Int = 1, val size: Int = 100) : Request() {

    init {
        path = "/cleanApp/task/queryBreakdowns"
    }
}