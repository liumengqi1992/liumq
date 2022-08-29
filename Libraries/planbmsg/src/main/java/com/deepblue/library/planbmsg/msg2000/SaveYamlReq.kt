package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class SaveYamlReq(filename:String, data:String): Request(2000, "配置导航平台参数") {
    init {
        json=Data(filename,data)
    }

    class Data(val filename:String,val data:String)
}