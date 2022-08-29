package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.YamlBack

class GetYamlRes : Response() {

    init {
        json = YamlBack()
    }

    fun getJson(): YamlBack? {
        return JsonUtils.fromJson(json.toString(), YamlBack::class.java)
    }
}