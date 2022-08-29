package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request

class GetRoomReq(map_id: Int) : Request(3018, "房号查询") {
    init {
        json = Data(map_id)
    }
    class Data(val map_id: Int)
}