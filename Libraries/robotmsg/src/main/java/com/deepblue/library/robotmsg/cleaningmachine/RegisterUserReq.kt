package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.Request

/**
 * 用户注册/注销/登录/登出/修改/查询注册信息
 */
class RegisterUserReq : Request(5013, "robot_cleaningmachine_registeruser_req") {

    companion object {
        private const val OPERATE_REGISTER = "register"
        private const val OPERATE_UNREGISTER = "unregister"
        private const val OPERATE_LOGIN = "login"
        private const val OPERATE_LOGOUT = "logout"
        private const val OPERATE_MODIFY = "modify"
        private const val OPERATE_QUERY = "query"
    }

    fun register(name: String, passwd: String, phone: String, email: String, user_type: Int) {
        json = Data(OPERATE_REGISTER, name, passwd, phone, email, user_type)
    }

    fun unregister(name: String) {
        json = Data(OPERATE_UNREGISTER, name)
    }

    fun login(name: String, passwd: String) {
        json = Data(OPERATE_LOGIN, name, passwd)
    }

    fun logout(name: String) {
        json = Data(OPERATE_LOGOUT, name)
    }

    fun modify(name: String, passwd: String?, phone: String?, email: String?, user_type: Int?, origin_passwd: String?) {
        json = Data(OPERATE_MODIFY, name, passwd, phone, email, user_type, origin_passwd)
    }

    fun query(name: String) {
        json = Data(OPERATE_QUERY, name)
    }

    /**
     * 数据区
     * @param operate 操作
     * @param name 用户名
     * @param passwd 6 位密码
     * @param phone 电话
     * @param email 邮箱
     * @param user_type 注册用户类型
     * @param origin_passwd 原密码
     */
    class Data(val operate: String, val name: String, val passwd: String? = null, val phone: String? = null, val email: String? = null, val user_type: Int? = null, val origin_passwd: String? = null)
}
