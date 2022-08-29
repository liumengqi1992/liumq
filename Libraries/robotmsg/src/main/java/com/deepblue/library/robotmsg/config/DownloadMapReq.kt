package com.deepblue.library.robotmsg.config

import com.deepblue.library.robotmsg.Request

/**
 * 从机器人下载地图
 */
class DownloadMapReq(map_name: String, index: Int = 0, current_map: Boolean = false) : Request(3001, "robot_config_downloadmap_req") {

    init {
        number = index
        json = Data(map_name, current_map)
    }

    /**
     * 数据区
     * @param map_name 地图名
     * @param current_map true:下载机器人的当前地图,忽略 map_name 字段;false:字段无效。
     */
    class Data(val map_name: String, val current_map: Boolean)
}
