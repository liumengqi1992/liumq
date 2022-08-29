package com.deepblue.cloudmsg.robot

import com.deepblue.cloudmsg.Request

class HandleTaskStatus(val taskId:Int,val taskStatus:String) : Request() {
    init {
        path = "/robotCommunication/handleTaskStatus"
    }
}