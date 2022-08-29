package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.Map

/**
 * 拷贝机器人上的地图(13014)
 */
class CopyMapsRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        val maps = ArrayList<Map>()
    }
}