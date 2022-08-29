package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.Settings

/**
 * 查询导航平台参数
 */
class GetParamsRes : Response() {

    init {
        json = Settings()
    }

    fun getJson(): Settings? {
        return JsonUtils.fromJson(json.toString(), Settings::class.java)
    }
}