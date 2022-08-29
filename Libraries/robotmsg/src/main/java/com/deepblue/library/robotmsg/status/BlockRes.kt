package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 查询机器人被阻挡状态
 */
class BlockRes : Response() {

    companion object {
        const val BLOCKED_REASON_ULTRASONIC = 0
        const val BLOCKED_REASON_INFRARED = 1
        const val BLOCKED_REASON_LASER = 2
    }

    init {
        json = Block()
    }

    fun getJson(): Block? {
        return JsonUtils.fromJson(json.toString(), Block::class.java)
    }

    class Block {
        var blocked: Boolean = false//机器人是否被阻挡
        var blocked_reason: Int = 0//被阻挡的原因:0:超声波1:红外2:激光(可缺省)
    }
}