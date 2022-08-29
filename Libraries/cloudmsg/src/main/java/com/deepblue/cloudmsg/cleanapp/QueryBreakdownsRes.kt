package com.deepblue.cloudmsg.cleanapp

import com.deepblue.cloudmsg.Response
import com.deepblue.cloudmsg.bean.Breakdown
import com.deepblue.cloudmsg.bean.Device
import com.deepblue.library.planbmsg.JsonUtils

/**
 * 查询故障错误列表
 */
class QueryBreakdownsRes: Response() {

    init {
        data = Data()
    }

    fun getData(): Data? {
        return JsonUtils.fromJson(data.toString(), Data::class.java)
    }

    class Data {
        var pageInfo = PageInfo()
        var noReadCount = 0
    }

    class PageInfo {
        var total = 0
        var pageNum = 0
        var pageSize = 0
        var pages = 0
        val rows = ArrayList<Breakdown>()
    }
}