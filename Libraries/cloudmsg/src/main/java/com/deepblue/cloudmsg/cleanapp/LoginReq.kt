package com.deepblue.cloudmsg.cleanapp

import com.deepblue.cloudmsg.Request
import com.deepblue.cloudmsg.utils.DesEncryptDecrypt
import android.text.TextUtils

/**
 * 用户登录
 */
class LoginReq(val mobile: String, password: String?, val messageCode: String?) : Request() {

    init {
        path = "/cleanApp/user/login"
    }

    val password: String? = if (TextUtils.isEmpty(password)) null else DesEncryptDecrypt.getInstance().encrypt(password)
}