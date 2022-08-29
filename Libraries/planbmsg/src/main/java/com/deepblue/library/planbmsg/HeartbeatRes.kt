package com.deepblue.library.planbmsg

/**
 * 心跳包
 */
class HeartbeatRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        //时间戳，自纪元 Epoch（1970-01-01 00:00:00 UTC）起经过的时间，以秒为单位
        var time = 0L
    }
}