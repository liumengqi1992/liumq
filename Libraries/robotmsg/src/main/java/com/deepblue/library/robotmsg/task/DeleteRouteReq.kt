package com.deepblue.library.robotmsg.task

import com.deepblue.library.robotmsg.Request

class DeleteRouteReq(mapname: String, taskname: String) : Request(2009, "robot_control_deletetask_req ") {
    init {
        json = Data(mapname, mapname, taskname)
    }

    class Data(var map_name: String, var task_map_name: String, var task_name: String)
}