package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.FuxiStatus6

/**
 * 查询推送状态(福喜机器人专用)
 * 命令类型:14006
 */
class GetPushStatusFuxiRes6 : Response() {

    init {
        json = FuxiStatus6()
    }

    fun getJson(): FuxiStatus6? {
        return JsonUtils.fromJson(json.toString(), FuxiStatus6::class.java)
    }
}