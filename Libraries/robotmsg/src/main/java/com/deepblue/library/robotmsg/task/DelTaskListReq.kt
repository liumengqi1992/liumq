package com.deepblue.library.robotmsg.task

import com.deepblue.library.robotmsg.Request

class DelTaskListReq(map_name: String, task_chain_name: String) : Request(5004, "robot_app_deletetaskchain_req") {

    init {
        json = Data(map_name, task_chain_name)
    }

    class Data(var map_name: String, var task_chain_name: String)


}