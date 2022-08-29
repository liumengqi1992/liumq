package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 查询机器人位姿
 */
class LocRes : Response() {

    init {
        json = Loc()
    }

    fun getJson(): Loc? {
        return JsonUtils.fromJson(json.toString(), Loc::class.java)
    }

    class Loc {
        var x: Double = 0.0//机器人 x 坐标
        var y: Double = 0.0//机器人 y 坐标
        var angle: Double = 0.0//机器人的角度,单位 rad
        var confidence: Double = 0.0//机器人定位姿信度,[0,1]
    }
}