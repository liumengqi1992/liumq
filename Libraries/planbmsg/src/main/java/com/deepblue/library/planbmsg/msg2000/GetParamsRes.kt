package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.ConfigParam

/**
 * 查询导航平台参数(12013)
 */
class GetParamsRes : Response() {

    init {
        json = ConfigParam()
    }

    fun getJson(): ConfigParam? {
        return JsonUtils.fromJson(json.toString(), ConfigParam::class.java)
    }
}