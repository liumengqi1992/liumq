package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.FuxiStatus4

/**
 * 查询推送状态(福喜机器人专用)
 * 命令类型:14004
 */
class GetPushStatusFuxiRes4 : Response() {

    init {
        json = FuxiStatus4()
    }

    fun getJson(): FuxiStatus4? {
        return JsonUtils.fromJson(json.toString(), FuxiStatus4::class.java)
    }
}