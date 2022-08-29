package com.deepblue.cloudmsg.robot

import com.deepblue.cloudmsg.Request
class HandleDeviceRealData(val deviceId: Int, val batteryLevel: String) :Request() {
    init {
        path = "/robotCommunication/handleDeviceRealData"
    }
}