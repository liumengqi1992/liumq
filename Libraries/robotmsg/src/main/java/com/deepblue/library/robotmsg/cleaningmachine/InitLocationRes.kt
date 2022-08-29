package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 初始定位是否正确
 */
class InitLocationRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        var value: Boolean = false//true:初始位置正确,false:初始位置不对
        var score: Int = 0//0-100,超过 50 就算定位正常
    }
}