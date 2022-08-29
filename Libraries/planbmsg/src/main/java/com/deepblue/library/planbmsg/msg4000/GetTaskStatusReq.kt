package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.Request

class GetTaskStatusReq(id: Int) : Request(4003, "获取任务状态") {

    init {
        json = Data(id)
    }

    /**
     * 数据区
     * @param id 任务ID
     */
    class Data(val id: Int)
}