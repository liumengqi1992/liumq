package com.deepblue.library.planbmsg.msg1000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response

/**
 * 查询陀螺仪数据（11007）
 */
class GetGyroDataRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        //角度°
        var angle = 0.0
        //1-正常，0-故障
        var status = 1
    }
}