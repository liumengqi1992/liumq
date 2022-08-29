package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.Request

class GetAllTasksReq(map_id: Int) : Request(4005, "查询所有任务") {

    init {
        json = Data(map_id)
    }

    /**
     * 数据区
     * @param map_id 任务ID
     */
    class Data(val map_id: Int)
}