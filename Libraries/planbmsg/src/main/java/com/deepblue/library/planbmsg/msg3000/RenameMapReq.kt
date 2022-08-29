package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request

class RenameMapReq(map_id: Int, map_name: String, index: Int) : Request(3007, "地图重命名") {

    init {
        number = index
        json = Data(map_id, map_name)
    }

    /**
     * 数据区
     * @param map_id 地图ID
     * @param map_name 地图名
     */
    class Data(val map_id: Int, val map_name: String)
}