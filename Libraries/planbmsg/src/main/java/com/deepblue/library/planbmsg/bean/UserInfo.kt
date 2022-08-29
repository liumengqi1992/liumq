package com.deepblue.library.planbmsg.bean

class UserInfo {

    companion object {
        const val ROOT = 0
        const val ADMIN = 1
        const val MANAGER = 2
        const val USER = 3

        const val REGISTER = 1
        const val MODIFY = 2
    }
    //用户名
    var name = ""
    //密码
    var passwd = ""
    //电话
    var phone = ""
    //邮箱
    var email = ""
    //用户类型
    var user_type = 0

    //用户注册时间
    var registration_data = ""
    //用户最后登录时间
    var login_data = ""
}