package com.deepblue.library.robotmsg.control

import com.deepblue.library.robotmsg.Request

/**
 * 发送自由导航目标点
 */
class SendGoalReq(x: Double, y: Double, angle: Double) : Request(2007, "robot_control_sendgoal_req") {

    init {
        json = Data(x, y, angle)
    }

    /**
     * 数据区
     * @param x 在世界坐标系中目标点的 x 坐标,单位 m
     * @param y 在世界坐标系中目标点的 y 坐标,单位 m
     * @param angle 在世界坐标系中目标点的角度,单位 rad
     */
    class Data(val x: Double, val y: Double, val angle: Double)
}
