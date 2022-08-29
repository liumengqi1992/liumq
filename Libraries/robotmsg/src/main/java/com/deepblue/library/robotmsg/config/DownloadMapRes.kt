package com.deepblue.library.robotmsg.config

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.Map

/**
 * 从机器人下载地图
 */
class DownloadMapRes : Response() {

    init {
        json = Map()
    }

    fun getJson(): Map? {
        val map = JsonUtils.fromJson(json.toString(), Map::class.java)
        map?.resolution = map?.header?.resolution?:0.0
        return map
    }
}