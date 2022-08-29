package com.deepblue.library.robotmsg.config

import com.deepblue.library.robotmsg.Request

/**
 * 地图重命名/复制
 */
class RenameMapReq(origin_name: String, new_name: String, isCopy: Boolean, number: Int) : Request(3009, "robot_config_renamemap_req") {

    init {
        this.number = number
        json = Data(origin_name, new_name, isCopy)
    }

    /**
     * 数据区
     * @param origin_name 原地图名字
     * @param new_name 新地图名字
     * @param save_origin_map 是否保留原地图,可以用来复制地图
     */
    class Data(val origin_name: String, val new_name: String, val save_origin_map: Boolean)
}
