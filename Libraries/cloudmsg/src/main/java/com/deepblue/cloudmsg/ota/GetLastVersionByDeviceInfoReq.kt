package com.deepblue.cloudmsg.ota

import com.deepblue.cloudmsg.Request

/**
 * 通过设备信息获取最新版本信息
 */
class GetLastVersionByDeviceInfoReq(val deviceSeries: String, val deviceModel: String, val deviceVersion: String, val packageType: String) : Request() {

    init {
        path = "/api/ota/diningCarOta/getLastVersionByDeviceInfo"
    }
}