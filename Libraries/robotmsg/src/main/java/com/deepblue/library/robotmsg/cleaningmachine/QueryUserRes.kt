package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

class QueryUserRes : Response() {

    init {
        json = User()
    }

    fun getJson(): User? {
        return JsonUtils.fromJson(json.toString().replace(" ", ""), User::class.java)
    }

    class User {
        var email: String = ""
        var name: String = ""
        var passwd: String = ""
        var phone: String = ""
        var user_type: Int = -1
    }

}