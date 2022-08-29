package com.deepblue.cloudmsg.merchant

import com.deepblue.cloudmsg.Response
import com.deepblue.cloudmsg.bean.MsgInfo
import com.deepblue.library.planbmsg.JsonUtils

/**
 * 查询消息列表
 */
class QueryMsgInfoListRes: Response() {

    init {
        data = Data()
    }

    fun getData(): Data? {
        return JsonUtils.fromJson(data.toString(), Data::class.java)
    }

    class Data {
        var total = 0
        var pageNum = 0
        var pageSize = 0
        var pages = 0
        val rows = ArrayList<MsgInfo>()
    }
}