package com.deepblue.library.planbmsg.msg1000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.RobotStatus

/**
 * 查询机器人工作状态(11004)
 */
class GetRobotStatusRes : Response() {

    init {
        json = RobotStatus()
    }

    fun getJson(): RobotStatus? {
        return JsonUtils.fromJson(json.toString(), RobotStatus::class.java)
    }
}