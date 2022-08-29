package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.Goline

class SetOtherOutRes : Response() {
    init {
        json = Goline()
    }

    fun getJson(): Goline? {
        return JsonUtils.fromJson(json.toString(), Goline::class.java)
    }
}