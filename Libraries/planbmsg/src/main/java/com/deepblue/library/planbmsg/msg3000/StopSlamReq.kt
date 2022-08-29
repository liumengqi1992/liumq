package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request

class StopSlamReq(save_map: Boolean = true) : Request(3005, "停止扫地图") {

    init {
        json = Data(save_map)
    }

    /**
     * 数据区
     * @param save_map 是否需要保存这张地图
     */
    class Data(val save_map: Boolean)
}