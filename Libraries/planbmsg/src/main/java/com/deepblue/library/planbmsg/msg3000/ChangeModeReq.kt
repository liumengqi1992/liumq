package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request

class ChangeModeReq(mode: String) : Request(3013, "切换手动自动模式") {

    init {
        json = Data(mode)
    }

    class Data(val mode: String)
}
