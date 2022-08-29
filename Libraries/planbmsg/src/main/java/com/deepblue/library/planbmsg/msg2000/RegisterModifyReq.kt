package com.deepblue.library.planbmsg.msg2000

import com.alibaba.fastjson.annotation.JSONField
import com.deepblue.library.planbmsg.Request
import com.deepblue.library.planbmsg.bean.UserInfo

class RegisterModifyReq(@JSONField(serialize = false) private val user: UserInfo) : Request(2001, "用户注册/修改注册信息") {

    fun register(): String {
        number = UserInfo.REGISTER
        json = Data("register", user)
        return toString()
    }

    fun modify(): String {
        number = UserInfo.MODIFY
        json = Data("modify", user)
        return toString()
    }

    /**
     * 数据区
     * @param operate 注册/修改
     * @param name 用户名
     * @param passwd 密码
     * @param phone 电话
     * @param email 邮箱
     * @param user_type 用户类型
     */
    class Data(val operate: String, user: UserInfo) {
        val name = user.name
        val passwd = user.passwd
        val phone = user.phone
        val email = user.email
        val user_type = user.user_type
    }
}