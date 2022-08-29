package com.deepblue.cloudmsg.robot

import com.deepblue.cloudmsg.Request

class HandleDeviceWorkStatus(val deviceId: Int, val deviceWorkStatus: String) : Request()  {
    init {
        path = "/robotCommunication/handleDeviceWorkStatus"
    }
}