package com.deepblue.library.robotmsg.config

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.Settings

class SettingRes : Response() {
    init {
        json = Settings()
    }

    fun getJson(): Settings? {
        return JsonUtils.fromJson(json.toString(), Settings::class.java)
    }

}