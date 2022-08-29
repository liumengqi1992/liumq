package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.Request

class GetTaskReq : Request(4004, "查询单个任务") {

    fun id(id: Int, position: Int): String {
        number = position
        json = Data(id)
        return toString()
    }

    fun current(): String {
        json = Data(0)
        return toString()
    }

    /**
     * 数据区
     * @param id 任务ID
     */
    class Data(val id: Int)
}