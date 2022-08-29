package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.Request

/**
 * 获取历史报告
 */
class GetHistoryReportReq(from: Long, to: Long) : Request(5023, "robot_cleaningmachine_gethistoryreport_req") {

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
