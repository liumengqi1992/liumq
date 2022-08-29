package com.deepblue.library.planbmsg.msg5000

import com.deepblue.library.planbmsg.Request

class SetCleanModeReq(mode: Int) : Request(5010, "设置机器人清洁模式（通用）") {

    init {
        json = Data(mode)
    }

    class Data(val mode: Int)
}