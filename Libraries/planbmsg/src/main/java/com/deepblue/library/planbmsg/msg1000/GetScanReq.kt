package com.deepblue.library.planbmsg.msg1000

import com.deepblue.library.planbmsg.Request

class GetScanReq : Request(1003, "查询激光数据") {

    companion object {
        const val START = 1
        const val STOP = 2
    }

    fun start(): String {
        number = START
        json = Data("start")
        return toString()
    }

    fun stop(): String {
        number = STOP
        json = Data("stop")
        return toString()
    }

    /**
     * 数据区
     * @param operate
     * 开始:”start”,开始，后面会一直推送激光数据
     * 结束:”stop”,结束，停止激光推送
     */
    class Data(val operate: String)
}