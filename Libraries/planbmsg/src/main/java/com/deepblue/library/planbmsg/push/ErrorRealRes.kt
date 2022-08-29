package com.deepblue.library.planbmsg.push

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.ErrorInfo

/**
 * 推送实时故障状态(24002)
 */
class ErrorRealRes : Response() {

    init {
        json = ErrorInfo()
    }
    fun getJson(): ErrorInfo? {
        val errorInfo = JsonUtils.fromJson(json.toString(), ErrorInfo::class.java) ?: return null
        return errorInfo
    }
    fun getJson(robotId: String): ErrorInfo? {
        val errorInfo = JsonUtils.fromJson(json.toString(), ErrorInfo::class.java) ?: return null
        errorInfo.robotId = robotId
        return errorInfo
    }
}