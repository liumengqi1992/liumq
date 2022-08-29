package com.deepblue.library.robotmsg.config

import com.deepblue.library.robotmsg.Request

/**
 * 删除机器人上的地图
 */
class DeleteMapReq(map_name: String, index: Int) : Request(3002, "robot_config_deletemap_req") {

    init {
        number = index
        json = Data(map_name)
    }

    /**
     * 数据区
     * @param map_name 地图名
     */
    class Data(val map_name: String)
}
