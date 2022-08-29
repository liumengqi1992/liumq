package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 查询机器人任务状态
 */
class GetLogRes : Response() {

    init {
        json = Log()
    }

    fun getJson(): Log? {
        return JsonUtils.fromJson(json.toString(), Log::class.java)
    }

    class Log {
        var fatal: String = ""//获取机器人上的致命日志(可缺省)
        var error: String = ""//获取机器人上的错误日志(可缺省)
        var warn: String = ""//获取机器人上的警告日志(可缺省)
        var info: String = ""//获取机器人上的消息日志(可缺省)
    }
}
