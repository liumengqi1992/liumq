package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.Request

class GetTaskReportsReq2(from: Long = 0, to: Long = 0) : Request(4011, "获取任务报告") {

    init {
        json = Data(from, to)
    }

    class Data(val from: Long, val to: Long)
}