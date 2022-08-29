package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 查询历史故障信息
 */
class GetErrorHistoryRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        var error_msgs = ArrayList<ErrorMsg>()
    }

    class ErrorMsg {
        var id = -1
        var time: Long = 0
        var reason: String = ""
        var suggestion: String? = ""
        var error_code: Int = 0
    }
}