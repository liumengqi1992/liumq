package com.deepblue.library.robotmsg.task

import com.deepblue.library.robotmsg.Request

class TaskListReq(map_name: String) : Request(5009, "robot_app_getalltaskchain_req") {
    init {
        json = Data(map_name)
    }

    class Data(val map_name: String)
}