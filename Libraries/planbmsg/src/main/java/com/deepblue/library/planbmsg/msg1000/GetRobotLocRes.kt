package com.deepblue.library.planbmsg.msg1000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.RobotLoc

/**
 * 查询机器人位姿(11001)
 */
class GetRobotLocRes : Response() {

    init {
        json = RobotLoc()
    }

    fun getJson(): RobotLoc? {
        return JsonUtils.fromJson(json.toString(), RobotLoc::class.java)
    }
}