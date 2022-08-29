package com.deepblue.library.robotmsg.control

import com.deepblue.library.robotmsg.Request

/**
 * 重定位
 */
class RelocReq(x: Double, y: Double, angle: Double, reloc_waypoint: String? = null) : Request(2008, "robot_control_reloc_req") {

    init {
        json = Data(x, y, angle, reloc_waypoint)
    }

    /**
     * 数据区
     * @param x 在世界坐标系中目标点的 x 坐标,单位 m
     * @param y 在世界坐标系中目标点的 y 坐标,单位 m
     * @param angle 在世界坐标系中目标点的角度,单位 rad
     * @param reloc_waypoint 指定重定位的位点名字
     */
    class Data(val x: Double, val y: Double, val angle: Double, val reloc_waypoint: String? = null)
}
