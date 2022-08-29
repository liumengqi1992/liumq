package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.NavigationStatus

/**
 * 查询推送状态(导航平台专用)
 * 命令类型:14000
 */
class GetPushStatusNavigationRes : Response() {

    init {
        json = NavigationStatus()
    }

    fun getJson(): NavigationStatus? {
        return JsonUtils.fromJson(json.toString(), NavigationStatus::class.java)
    }
}