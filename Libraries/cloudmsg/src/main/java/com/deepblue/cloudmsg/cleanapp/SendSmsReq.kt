package com.deepblue.cloudmsg.cleanapp

import com.deepblue.cloudmsg.Request

/**
 * 发送短信验证码
 */
class SendSmsReq(val mobile: String) : Request() {
    init {
        path = "/cleanApp/user/sendSms"
    }
}