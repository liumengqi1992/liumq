package com.deepblue.library.planbmsg.msg1000

import com.deepblue.library.planbmsg.Request

class GetRobotLocReq : Request(1001, "查询机器人位姿") {

    companion object {
        const val START = 1
        const val STOP = 2
        const val QUERY = 3
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

    fun query():String{
        number = QUERY
        json = Data("query")
        return toString()
    }

    /**
     * 数据区
     * @param operate
     * 开始:”start”,开始，后面会一直推送机器人位姿
     * 结束:”stop”,结束，停止激光推送
     */
    class Data(val operate: String)
}