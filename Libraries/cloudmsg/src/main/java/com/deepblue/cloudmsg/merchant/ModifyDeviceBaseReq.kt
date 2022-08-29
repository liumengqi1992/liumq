package com.deepblue.cloudmsg.merchant

import com.deepblue.cloudmsg.Request

/**
 * 修改设备基本信息
 */
class ModifyDeviceBaseReq(val id: Int, val deviceMerchantId: Int) : Request() {

    var deviceName: String? = null

    init {
        path = "/merchant/device/modifyDeviceBase"
    }

    fun rename(deviceName: String): Request {
        this.deviceName = deviceName
        return this
    }
}