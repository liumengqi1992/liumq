package com.deepblue.library.robotmsg.task

import com.deepblue.library.robotmsg.Request
import com.deepblue.library.robotmsg.bean.Map
import com.deepblue.library.robotmsg.config.ChangeMapPointReq

class RelocationReq(mapPos: Map.Pos, rotated: Boolean = false) : Request(2008, "robot_control_reloc_req") {

    init {
        json = Data(mapPos.x, mapPos.y, mapPos.angle?:0.0, rotated)
    }

    class Data(val x: Double, val y: Double, val angle: Double, val rotated: Boolean)
}