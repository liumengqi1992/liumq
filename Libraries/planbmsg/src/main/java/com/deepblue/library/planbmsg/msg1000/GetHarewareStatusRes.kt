package com.deepblue.library.planbmsg.msg1000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.HardwareStatus

/**
 * 查询机器人硬件状态(11009)
 */
class GetHarewareStatusRes : Response() {

    init {
        json = HardwareStatus()
    }

    fun getJson(): HardwareStatus? {
        return JsonUtils.fromJson(json.toString(), HardwareStatus::class.java)
    }
}