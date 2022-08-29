package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class ConnectWifiReq(wifi: String, password: String) : Request(2025, "连接WIFI") {
    init {
        json = Data(wifi, password)
    }

    class Data(val wifi_name: String, val passwd: String)
}