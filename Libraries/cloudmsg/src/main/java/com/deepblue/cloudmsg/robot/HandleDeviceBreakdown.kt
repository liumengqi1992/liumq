package com.deepblue.cloudmsg.robot

import com.deepblue.cloudmsg.Request

class HandleDeviceBreakdown(val deviceId: Int, val breakdownCode: String) : Request() {
    init {
        path ="/robotCommunication/handleDeviceBreakdown"
    }
}