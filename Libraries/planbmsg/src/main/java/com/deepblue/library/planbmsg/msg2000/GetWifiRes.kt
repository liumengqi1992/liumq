package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.WifiList

class GetWifiRes : Response() {
    init {
        json = WifiList()
    }

    fun getJson(): WifiList? {
        return JsonUtils.fromJson(json.toString(), WifiList::class.java)
    }
}