package com.deepblue.library.planbmsg.msg1000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.RobotInfo

/**
 * 查询机器人信息(11000)
 */
class GetRobotInfoRes : Response() {

    init {
        json = RobotInfo()
    }

    fun getJson(): RobotInfo? {
        return JsonUtils.fromJson(json.toString(), RobotInfo::class.java)
    }
}