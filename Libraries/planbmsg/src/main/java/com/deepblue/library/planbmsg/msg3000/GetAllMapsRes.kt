package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.Map

/**
 * 查询机器人上所有的地图(13003)
 */
class GetAllMapsRes : Response() {

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