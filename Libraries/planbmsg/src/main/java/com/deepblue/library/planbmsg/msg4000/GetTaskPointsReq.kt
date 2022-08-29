package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.Request

@Deprecated("接口废弃，使用4006即可")
class GetTaskPointsReq(id: Int, position: Int) : Request(4007, "查询任务路径") {

    init {
        number = position
        json = Data(id)
    }

    /**
     * 数据区
     * @param id 任务id
     */
    class Data(val id: Int)
}