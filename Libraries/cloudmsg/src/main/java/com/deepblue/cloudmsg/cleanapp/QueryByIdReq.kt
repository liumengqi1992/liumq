package com.deepblue.cloudmsg.cleanapp

import com.deepblue.cloudmsg.Request

/**
 * 查询用户详情
 */
class QueryByIdReq(val userId: Int) : Request() {

    init {
        path = "/cleanApp/user/queryById"
    }
}