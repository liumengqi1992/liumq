package com.deepblue.library.robotmsg.task

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.RobotPlayStatus

class RobotStatusRes : Response() {
    init {
        json = RobotPlayStatus()
    }

    fun getJson(): RobotPlayStatus? {
        return JsonUtils.fromJson(json.toString(), RobotPlayStatus::class.java)
    }


}