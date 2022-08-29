package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request
import com.deepblue.library.planbmsg.bean.WayPoint

class GotoSpecialPointReq(type: String = WayPoint.Type_Charge) : Request(2022, "前往特殊点") {

    init {
        json = Data(type)
    }

    class Data(val type: String)
}