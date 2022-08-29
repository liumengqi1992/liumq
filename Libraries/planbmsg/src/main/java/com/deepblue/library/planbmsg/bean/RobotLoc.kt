package com.deepblue.library.planbmsg.bean

class RobotLoc : AngleMapPoint() {

    //机器人定位置信度，[0,1]
    var confidence = 0.0

    fun copy(value: RobotLoc) {
        this.x = value.x
        this.y = value.y
        this.angle = value.angle
        this.confidence = value.confidence
    }
}