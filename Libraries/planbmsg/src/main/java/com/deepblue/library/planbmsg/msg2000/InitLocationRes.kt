package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response

/**
 * 初始定位是否正确(12008)
 */
class InitLocationRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        //true：初始位置正确，false：初始位置不对
        var value = false
        //0-100，超过 50 就算定位正常
        var score = 0
    }
}