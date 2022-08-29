package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request

class StartSlamReq(map_name: String) : Request(3004, "开始扫地图") {

    init {
        json = Data(map_name)
    }

    /**
     * 数据区
     * @param map_name 地图名
     */
    class Data(val map_name: String)
}