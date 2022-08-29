package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class GetParamsReq : Request(2013, "查询导航平台参数") {

    fun list(): GetParamsReq {
        json = Data("list")
        return this
    }

    fun file(filename: String): GetParamsReq {
        json = Data("query", filename)
        return this
    }

    class Data(val operate: String, val filename: String? = null)
}