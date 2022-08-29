package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.Map

/**
 * 从机器人下载地图(13009)
 */
class DownloadMapRes : Response() {

    init {
        json = Map()
    }

    fun getJson(): Map? {
        val map = JsonUtils.fromJson(json.toString(), Map::class.java)
        map?.resolution = map?.map_info?.resolution?:1.0
        return map
    }
}