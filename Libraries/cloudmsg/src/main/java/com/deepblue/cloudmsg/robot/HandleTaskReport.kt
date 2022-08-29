package com.deepblue.cloudmsg.robot

import com.deepblue.cloudmsg.Request
import com.deepblue.cloudmsg.bean.ReportJson

class HandleTaskReport(val deviceId:Int,val taskId:Int,val workDay:String,val reportJson :ArrayList<ReportJson>): Request() {
    init {
        path = "/robotCommunication/handleTaskReport"
    }
}