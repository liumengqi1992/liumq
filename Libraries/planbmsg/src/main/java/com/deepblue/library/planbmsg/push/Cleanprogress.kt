package com.deepblue.library.planbmsg.push

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.CleanProInfo

class Cleanprogress : Response() {

    init {
        json = CleanProInfo()
    }

    fun getJson(): CleanProInfo? {
        val errorInfo = JsonUtils.fromJson(json.toString(), CleanProInfo::class.java) ?: return null
        return errorInfo
    }
}
