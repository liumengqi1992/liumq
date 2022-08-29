package com.deepblue.library.robotmsg.control

import com.deepblue.library.robotmsg.Request

/**
 * 删除任务
 */
class DeleteTaskReq(task_name: String) : Request(2009, "robot_control_deletetask_req") {

    init {
        json = Data(task_name)
    }

    /**
     * 数据区
     * @param task_name 要删除的任务的名字
     */
    class Data(val task_name: String)
}
