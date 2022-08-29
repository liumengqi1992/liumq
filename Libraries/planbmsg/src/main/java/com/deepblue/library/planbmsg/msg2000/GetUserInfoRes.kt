package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.UserInfo

/**
 * 查询注册信息(12005)
 */
class GetUserInfoRes : Response() {

    init {
        json = UserInfo()
    }

    fun getJson(): UserInfo? {
        return JsonUtils.fromJson(json.toString(), UserInfo::class.java)
    }
}