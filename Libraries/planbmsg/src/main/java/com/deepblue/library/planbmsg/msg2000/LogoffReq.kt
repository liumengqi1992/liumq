package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class LogoffReq(name: String, position: Int = 0) : Request(2002, "用户注销") {

    init {
        number = position
        json = Data(name)
    }

    /**
     * 数据区
     * @param name 用户名
     */
    class Data(val name: String)
}