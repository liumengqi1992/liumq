package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.Request
import com.deepblue.library.planbmsg.bean.TaskBasicInfo

class GetAllTasksByTypeReq(map_id: Int, type: Int = TaskBasicInfo.TASK_TYPE_WASH) : Request(4010, "查询特定任务类型的所有任务") {

    constructor(map_id: Int): this(map_id, TaskBasicInfo.TASK_TYPE_WASH)

    init {
        json = Data(map_id, type)
    }

    /**
     * 数据区
     * @param map_id 任务ID
     * @param type 任务类型
     */
    class Data(val map_id: Int, val type: Int)
}