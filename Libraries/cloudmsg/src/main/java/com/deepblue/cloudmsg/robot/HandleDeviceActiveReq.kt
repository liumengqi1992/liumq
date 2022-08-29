package com.deepblue.cloudmsg.robot

import com.deepblue.cloudmsg.Request

/**
 * 设备激活
 */
class HandleDeviceActiveReq(val deviceMac: String, val versionNum: String = "V16.1") : Request() {

    init {
        path = "/robotCommunication/handleDeviceActive"
    }
}