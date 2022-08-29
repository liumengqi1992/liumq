package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request

/**
 * 从机器人下载地图
 */
class DownloadMapReq : Request(3009, "从机器人下载地图") {

    fun map(map_id: Int, position: Int = 0): String {
        number = position
        json = Data(map_id, false)
        return toString()
    }

    fun number(room_number: String): String {
        json = Data2(room_number)
        return toString()
    }

    fun current(): String {
        json = Data(0, true)
        return toString()
    }

    /**
     * 数据区
     * @param map_id 地图ID
     * @param current_map true:下载机器人的当前地图,忽略 map_name 字段;false:字段无效。
     */
    class Data(val map_id: Int, val current_map: Boolean)

    class Data2(val room_number: String)
}
