package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.FuxiStatus3

/**
 * 查询推送状态(福喜机器人专用)
 * 命令类型:14003
 */
class GetPushStatusFuxiRes3 : Response() {

    init {
        json = FuxiStatus3()
    }

    fun getJson(): FuxiStatus3? {
        return JsonUtils.fromJson(json.toString(), FuxiStatus3::class.java)
    }
}