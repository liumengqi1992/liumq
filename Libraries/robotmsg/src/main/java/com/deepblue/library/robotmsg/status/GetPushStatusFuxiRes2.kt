package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.FuxiStatus2

/**
 * 查询推送状态(福喜机器人专用)
 * 命令类型:14002
 */
class GetPushStatusFuxiRes2 : Response() {

    init {
        json = FuxiStatus2()
    }

    fun getJson(): FuxiStatus2? {
        return JsonUtils.fromJson(json.toString(), FuxiStatus2::class.java)
    }
}