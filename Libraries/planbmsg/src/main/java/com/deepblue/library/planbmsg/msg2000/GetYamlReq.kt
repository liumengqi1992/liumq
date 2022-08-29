package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class GetYamlReq() : Request(2013, "查询导航平台参数") {


    fun list(filename: String): String {
        json = Data("list", filename)
        return toString()

    }

    fun query(filename: String): String {
        json = Data("query", filename)
        return toString()

    }

    class Data(val operate: String, val filename: String)

}