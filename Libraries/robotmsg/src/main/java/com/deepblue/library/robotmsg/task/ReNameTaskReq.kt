package com.deepblue.library.robotmsg.task

import com.deepblue.library.robotmsg.Request

class ReNameTaskReq(map_name: String, origin_name: String, new_name: String) :
    Request(5003, "robot_app_renametaskchain_req") {
    init {
        json = Data(map_name, origin_name,new_name)
    }

    class Data(var map_name: String, var origin_name: String,var new_name: String)


}