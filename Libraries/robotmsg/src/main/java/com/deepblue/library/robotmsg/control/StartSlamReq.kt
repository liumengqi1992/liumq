package com.deepblue.library.robotmsg.control

import com.deepblue.library.robotmsg.Request

/**
 * 开始扫地图
 */
class StartSlamReq(map_name: String) : Request(2001, "robot_control_startslam_req") {

    init {
        json = Data(map_name)
    }

    class Data(val map_name: String)
}
