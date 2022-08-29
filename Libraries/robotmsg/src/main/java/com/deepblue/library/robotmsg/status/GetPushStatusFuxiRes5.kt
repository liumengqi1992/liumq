package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.FuxiStatus5

/**
 * 查询推送状态(福喜机器人专用)
 * 命令类型:14005
 */
class GetPushStatusFuxiRes5 : Response() {

    init {
        json = FuxiStatus5()
    }

    fun getJson(): FuxiStatus5? {
        return JsonUtils.fromJson(json.toString(), FuxiStatus5::class.java)
    }
}