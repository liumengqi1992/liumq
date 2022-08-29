package com.deepblue.library.planbmsg.msg5000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response

/**
 * 查询洗地机工作模式(15005)
 */
class GetScrubberWorkModeRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        var mode = SetScrubberWorkModeReq.MODE_AUTO
    }
}