package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class GetUserInfoReq(name: String) : Request(2005, "查询注册信息") {

    init {
        json = Data(name)
    }

    /**
     * 数据区
     * @param name 用户名
     */
    class Data(val name: String)
}