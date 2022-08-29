package com.deepblue.cloudmsg.cleanapp

import com.deepblue.cloudmsg.Request

/**
 * 根据系列查询商户设备列表
 */
class QueryCleanRobotDeviceListBySeriesReq(val deviceSeriesId: Int = 0, val page: Int = 1, val size: Int = 100) : Request() {

    init {
        path = "/cleanApp/device/queryCleanRobotDeviceListBySeries"
    }
}