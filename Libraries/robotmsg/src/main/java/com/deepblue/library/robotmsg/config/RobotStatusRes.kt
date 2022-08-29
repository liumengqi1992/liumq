package com.deepblue.library.robotmsg.config

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.RobotStatus

class RobotStatusRes : Response() {
    init {
        json = RobotStatus()
    }

    fun getJson(): RobotStatus? {
        return JsonUtils.fromJson(json.toString(), RobotStatus::class.java)
    }
}