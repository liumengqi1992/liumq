package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request

class GetAllMapsReq(map_info: Boolean = false) : Request(3003, "查询机器人上所有的地图") {

    init {
        json = Data(map_info)
    }

    /**
     * 数据区
     * @param map_info 是否返回地图图片
     */
    class Data(val map_info: Boolean)
}