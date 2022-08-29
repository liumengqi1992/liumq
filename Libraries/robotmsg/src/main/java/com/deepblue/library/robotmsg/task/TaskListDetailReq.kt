package com.deepblue.library.robotmsg.task

import com.deepblue.library.robotmsg.Request

class TaskListDetailReq(map_name: String, task_chain_name: String, last_task_chain: Boolean) :
    Request(5010, "robot_app_downloadtaskchain_req") {
    init {
        json = Data(map_name, task_chain_name, last_task_chain)
    }

    class Data(var map_name: String, var task_chain_name: String, var last_task_chain: Boolean)

}