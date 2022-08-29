package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request
import com.deepblue.library.planbmsg.bean.AngleMapPoint

class RelocReq(angleMapPoint: AngleMapPoint, rotation: Boolean = false) : Request(3002, "重定位") {

    init {
        json = Data(angleMapPoint.x, angleMapPoint.y, angleMapPoint.angle, rotation)
    }

    /**
     * 数据区
     * @param x 在世界坐标系中目标点的 x 坐标,单位 m
     * @param y 在世界坐标系中目标点的 y 坐标,单位 m
     * @param angle 在世界坐标系中目标点的角度，单位 °
     * @param rotation 是否使用旋转重定位
     */
    class Data(val x: Double, val y: Double, val angle: Double, val rotation: Boolean = false)
}
