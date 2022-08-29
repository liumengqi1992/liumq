package com.deepblue.library.robotmsg.task

import com.deepblue.library.robotmsg.Request
import com.deepblue.library.robotmsg.bean.Map

class ToPointReq (mapPos: Map.Pos) : Request(2007, "robot_control_sendgoal_req") {
    init {
        json = mapPos
    }
}