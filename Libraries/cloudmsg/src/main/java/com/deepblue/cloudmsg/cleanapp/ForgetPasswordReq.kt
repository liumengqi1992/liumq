package com.deepblue.cloudmsg.cleanapp

import com.deepblue.cloudmsg.Request
import com.deepblue.cloudmsg.utils.DesEncryptDecrypt

/**
 * 忘记密码
 */
class ForgetPasswordReq(val mobile: String, newPassword: String, val smsCode: String) : Request() {

    init {
        path = "/cleanApp/user/forgetPassword"
    }

    val newPassword: String? = DesEncryptDecrypt.getInstance().encrypt(newPassword)
}