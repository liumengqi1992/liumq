package com.deepblue.library.robotmsg.control

import com.deepblue.library.robotmsg.Request

class GetPathReq(map_name: String, task_name: String, number: Int = 0) :
    Request(1011, "robot_status_gettaskpath_req") {

    init {
        this.number = number
        json = Data(map_name, task_name, false)
    }

    class Data(val task_map_name: String, val task_name: String, val default_task_path: Boolean)
}