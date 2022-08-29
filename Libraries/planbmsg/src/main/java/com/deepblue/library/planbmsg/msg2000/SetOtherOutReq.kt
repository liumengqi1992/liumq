package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class SetOtherOutReq : Request(2021, "其他设备下线请求")  {

    fun App(): String {
        json = Data(2)
        return toString()
    }

    fun robot(): String {
        json = Data(1)
        return toString()
    }
    class Data(val type: Int){

    }
}