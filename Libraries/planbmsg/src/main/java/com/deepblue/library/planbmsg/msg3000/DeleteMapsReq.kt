package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request

class DeleteMapsReq(map_id_list: ArrayList<Int>, mapsSize: Int) : Request(3006, "删除机器人上的地图") {

    init {
        number = mapsSize
        json = Data(map_id_list)
    }

    /**
     * 数据区
     * @param map_id 地图ID
     */
    class Data(val map_id_list: ArrayList<Int>)
}