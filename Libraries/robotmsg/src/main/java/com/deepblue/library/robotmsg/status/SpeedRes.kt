package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 查询机器人速度
 */
class SpeedRes : Response() {

    init {
        json = Speed()
    }

    fun getJson(): Speed? {
        return JsonUtils.fromJson(json.toString(), Speed::class.java)
    }

    class Speed {
        var vx: Double = 0.0//机器人 x 方向上的速度,单位 m/s
        var vy: Double = 0.0//机器人 y 方向上的速度,单位 m/s
        var vth: Double = 0.0//机器人 x 方向上的角速度,单位 rad/s
    }
}