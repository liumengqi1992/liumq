package com.deepblue.library.robotmsg.task

import com.deepblue.library.robotmsg.Request

class CheckMapReq (map_name:String, use_map_point:Boolean): Request(3005, "robot_config_changenavimap_req") {
    init {
        json = Data(map_name, use_map_point)
    }

    class Data(var map_name: String, var use_map_point:Boolean)

}