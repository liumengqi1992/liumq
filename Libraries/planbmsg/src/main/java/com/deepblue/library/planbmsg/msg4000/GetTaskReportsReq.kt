package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.Request

class GetTaskReportsReq(from: Int = 0, to: Int = 0) : Request(4011, "获取任务报告") {

    init {
        json = Data(from, to)
    }

    class Data(val from: Int, val to: Int)
}