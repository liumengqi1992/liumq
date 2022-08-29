package com.deepblue.library.planbmsg.msg5000

import com.deepblue.library.planbmsg.Request
import com.deepblue.library.planbmsg.bean.PointInfo

class SetMorePointReq(map_id: Int, info: List<PointInfo>) : Request(5008, "设置默认位点") {

    init {
        json = Data(map_id, info)
    }

    class Data(val map_id: Int, val pointinfo: List<PointInfo>)
}