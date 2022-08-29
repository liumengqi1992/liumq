package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.Response

/**
 * 用户注册/注销/登录/登出/修改/查询注册信息
 */
class RegisterUserRes : Response() {

    /**
     * "error_code":-1
     * 0:成功,
     * -1:其它错误
     * -2:登录错误,密码或者用户名错误
     * -3:注册错误,用户名已经存在
     */
}