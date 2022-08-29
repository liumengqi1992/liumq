package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.Request

/**
 * 查询历史故障信息
 */
class GetErrorHistoryReq(from: Long, to: Long) : Request(5020, "robot_cleaningmachine_geterrorhistory_req") {

    init {
        json = Data(from, to)
    }

    /**
     * 数据区
     * @param from 自纪元 Epoch(1970-01-01 00:00:00 UTC)起经过的时间,以秒为单位
     * @param to from==to==0,返回最新
     */
    class Data(val from: Long, val to: Long, val level: Int = 1)
}
