package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.Map

/**
 * 开始扫地图(13004)
 */
class StartSlamRes : Response() {

    init {
        json = Map()
    }

    fun getJson(): Map? {
        return JsonUtils.fromJson(json.toString(), Map::class.java)
    }
}