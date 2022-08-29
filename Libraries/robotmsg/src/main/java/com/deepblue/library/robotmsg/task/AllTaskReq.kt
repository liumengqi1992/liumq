package com.deepblue.library.robotmsg.task

import com.deepblue.library.robotmsg.Request

class AllTaskReq(map_name: String) : Request(1010, "robot_status_getalltasks_req") {
    init {
        json = Data(map_name)
    }

    class Data(val task_map_name: String)

}