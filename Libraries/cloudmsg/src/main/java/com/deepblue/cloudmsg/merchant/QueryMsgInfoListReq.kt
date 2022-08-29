package com.deepblue.cloudmsg.merchant

import com.deepblue.cloudmsg.Request

/**
 * 查询消息列表
 */
class QueryMsgInfoListReq(val page: Int = 1, val size: Int = 100) : Request() {

    init {
        path = "/merchant/msgInfo/queryMsgInfoList"
    }
}