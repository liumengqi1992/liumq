package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class SendUidReq(uid: String): Request(2019, "Uid发送") {
    init {
        json = Data(uid)
    }
    class Data(val uuid: String)
}