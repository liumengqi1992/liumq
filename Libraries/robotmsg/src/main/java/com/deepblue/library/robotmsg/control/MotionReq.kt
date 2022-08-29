package com.deepblue.library.robotmsg.control

import com.deepblue.library.robotmsg.Request

/**
 * 开环运动
 */
class MotionReq: Request {

    companion object {
        const val MAX_VX = 1.0
        const val MAX_VY = 1.0
        const val MAX_VTH = 1.0
        const val STEP = 100
    }

    constructor(percentVX: Int, percentVY: Int, percentVTH: Int): super(2000, "robot_control_motion_req") {
        json = Data(percentVX * MAX_VX / STEP, percentVY * MAX_VY / STEP, percentVTH * MAX_VTH / STEP)
    }

    constructor(percentVX: Double, percentVY: Double, percentVTH: Double): super(2000, "robot_control_motion_req") {
        json = Data(percentVX, percentVY, percentVTH)
    }

//    init {
//        json = Data(percentVX * MAX_VX / STEP, percentVY * MAX_VY / STEP, percentVTH * MAX_VTH / STEP)
//    }

    /**
     * 数据区
     * @param vx 机器人在机器人坐标系中的 x 轴方向速度,若缺省则认为是 0,单位 m/s
     * @param vy 机器人在机器人坐标系中的 y 轴方向速度,若缺省则认为是 0,单位 m/s
     * @param vth 机器人在机器人坐标系中的角速度(即顺时针转为负,逆时针转为正),若缺省则认为是 0,单位 rad/s
     */
    class Data(val vx: Double, val vy: Double, val vth: Double)
}
