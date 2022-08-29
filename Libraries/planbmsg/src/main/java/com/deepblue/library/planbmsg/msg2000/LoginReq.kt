package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class LoginReq : Request(2003, "用户登录") {

    /**
     * 手机端登录
     */
    fun app(userName: String, password: String): String {
        json = Data(userName, password, 2)
        return toString()
    }

    /**
     * 一体机端登录
     */
    fun robot(userName: String, password: String): String {
        json = Data(userName, password, 1)
        return toString()
    }

    fun egg(userName: String, password: String): String {
        json = Data(userName, password, 666)
        return toString()
    }

    /**
     * 数据区
     * @param name 用户名
     * @param passwd 密码
     * @param type 1- 本体屏幕,2- APP
     */
    class Data(val name: String, val passwd: String, val type: Int)
}