package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.Request

class RecordTaskReq : Request(4008, "录制任务") {

    companion object {
        const val START = 1
        const val STOP = 2
    }

    fun start(name: String, map_id: Int): String {
        number = START
        json = Data("start", name, map_id)
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
     * @param name 任务名称
     */
    class Data(val operate: String, val name: String? = null, val map_id: Int? = null)
}