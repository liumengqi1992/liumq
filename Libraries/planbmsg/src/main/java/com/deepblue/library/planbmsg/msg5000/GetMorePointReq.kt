package com.deepblue.library.planbmsg.msg5000

import com.deepblue.library.planbmsg.Request

class GetMorePointReq(map_id: Int) : Request(5007, "查询默认位点") {

    init {
        json = Data(map_id)
    }

    class Data(val map_id: Int)
}