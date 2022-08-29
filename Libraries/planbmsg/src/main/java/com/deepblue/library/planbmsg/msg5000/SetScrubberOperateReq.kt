package com.deepblue.library.planbmsg.msg5000

import com.deepblue.library.planbmsg.Request

class SetScrubberOperateReq : Request(5004, "开始/结束洗地") {

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
     * @param operate start-开始,stop-结束
     */
    class Data(val operate: String)
}