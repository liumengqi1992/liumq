package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.FuxiStatus1

/**
 * 查询推送状态(福喜机器人专用)
 * 命令类型:14001
 */
class GetPushStatusFuxiRes1 : Response() {

    init {
        json = FuxiStatus1()
    }

    fun getJson(): FuxiStatus1? {
        return JsonUtils.fromJson(json.toString(), FuxiStatus1::class.java)
    }
}