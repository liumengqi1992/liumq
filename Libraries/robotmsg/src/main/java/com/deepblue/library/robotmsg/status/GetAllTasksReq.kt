package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.Request

/**
 * 查询机器人上所有的任务
 */
class GetAllTasksReq(task_map_name: String) : Request(1010, "robot_status_getalltasks_req") {

    init {
        json = Data(task_map_name)
    }

    class Data(val task_map_name: String)
}
