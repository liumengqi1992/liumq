package com.deepblue.cloudmsg.cleanapp

import com.deepblue.cloudmsg.Response
import com.deepblue.cloudmsg.bean.Merchant
import com.deepblue.cloudmsg.bean.User
import com.deepblue.library.planbmsg.JsonUtils

/**
 * 用户登录
 */
class LoginRes: Response() {

    init {
        data = Data()
    }

    fun getData(): Data? {
        return JsonUtils.fromJson(data.toString(), Data::class.java)
    }

    class Data {
        var token = ""
        var user = User()
        var merchant = Merchant()
    }
}