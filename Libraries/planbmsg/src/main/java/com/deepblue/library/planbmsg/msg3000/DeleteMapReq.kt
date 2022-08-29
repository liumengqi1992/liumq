package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request

class DeleteMapReq(map_id: Int, position: Int) : Request(3006, "删除机器人上的地图") {

    init {
        number = position
        json = Data(map_id)
    }

    /**
     * 数据区
     * @param map_id 地图ID
     */
    class Data(val map_id: Int)
}