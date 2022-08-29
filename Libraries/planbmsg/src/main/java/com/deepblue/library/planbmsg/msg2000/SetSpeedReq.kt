package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class SetSpeedReq(speed: Float) : Request(2029, "设置机器人速度") {
    init {
        json = Data(speed)
    }

    class Data(val set_max_speed: Float)
}