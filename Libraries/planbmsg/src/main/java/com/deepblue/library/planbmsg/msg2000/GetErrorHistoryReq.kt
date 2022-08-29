package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class GetErrorHistoryReq(from: Long, to: Long) : Request(2009, "查询历史故障信息") {

    init {
        json = Data(from, to)
    }

    /**
     * 数据区
     * @param from 自纪元 Epoch(1970-01-01 00:00:00 UTC)起经过的时间,以秒为单位
     * @param to from==to==0,返回最新
     */
    class Data(val from: Long, val to: Long)
}