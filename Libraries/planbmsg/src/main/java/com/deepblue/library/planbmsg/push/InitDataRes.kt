package com.deepblue.library.planbmsg.push

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.InitData

/**
 * 查询机器人状态(24000)
 */
class InitDataRes : Response() {

    init {
        json = InitData()
    }

    fun getJson(): InitData? {
        return JsonUtils.fromJson(json.toString(), InitData::class.java)
    }
}