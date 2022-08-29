package com.deepblue.library.planbmsg.msg1000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.Sensor

class GetUltranRes : Response() {
    init {
        json = Sensor()
    }

    fun getJson(): Sensor? {
        return JsonUtils.fromJson(json.toString(), Sensor::class.java)
    }
}