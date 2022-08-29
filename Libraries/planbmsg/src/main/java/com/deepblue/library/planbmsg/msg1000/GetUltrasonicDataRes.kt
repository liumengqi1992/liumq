package com.deepblue.library.planbmsg.msg1000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.UltrasonicDatas
import com.deepblue.library.planbmsg.bean.WorkingData

/**
 * 查询超声波数据（11006）
 */
class GetUltrasonicDataRes : Response() {

    init {
        json = UltrasonicDatas()
    }

    fun getJson(): UltrasonicDatas? {
        return JsonUtils.fromJson(json.toString(), UltrasonicDatas::class.java)
    }
}