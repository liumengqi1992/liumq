package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request
import kotlin.math.roundToInt

class MotionReq: Request(3000, "开环运动") {

    companion object {
        const val MAX_VX = 1.0
        const val MAX_VY = 1.0
        const val MAX_VTH = 45.0
        const val STEP = 100
    }

    fun percent(percentVX: Int, percentVY: Int, percentVTH: Int): String {
        json = Data(percentVX * MAX_VX / STEP, percentVY * MAX_VY / STEP, (percentVTH * MAX_VTH / STEP).roundToInt().toDouble())
        return toString()
    }

    fun speed(vx: Double, vy: Double, vth: Double): String {
        json = Data(vx, vy, vth)
        return toString()
    }

    /**
     * 数据区
     * @param vx 机器人在机器人坐标系中的 x 轴方向速度,若缺省则认为是 0,单位 m/s
     * @param vy 机器人在机器人坐标系中的 y 轴方向速度,若缺省则认为是 0,单位 m/s
     * @param vth 机器人在机器人坐标系中的角速度(即顺时针转为负,逆时针转为正),若缺省则认为是 0,单位 rad/s
     */
    class Data(val vx: Double, val vy: Double, val vth: Double)
}
