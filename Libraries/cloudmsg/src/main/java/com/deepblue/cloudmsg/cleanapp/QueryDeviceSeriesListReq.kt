package com.deepblue.cloudmsg.cleanapp

import com.deepblue.cloudmsg.Request

/**
 * 查询商户下设备系列列表加设备数量
 */
class QueryDeviceSeriesListReq : Request() {

    init {
        path = "/cleanApp/device/queryDeviceSeriesList"
    }
}