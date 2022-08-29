package com.deepblue.library.mapbezier.utils

import android.graphics.Color
import android.graphics.Point

object PointUtils {

    //端点半径
    var PointRadius = 36f
    //端点
    var PointColor = Color.GRAY
    var PointStrokeWidth = 1f
    //辅助线
    var LineStrokeWidth = 2f
    //画笔
    var PaintColor = Color.GREEN
    var PaintStrokeWidth = 3f

    /**
     * 是否在圆内
     * @param x
     * @param y
     * @param point 圆心
     * @param radius 半径
     */
    fun isInCircle(x: Float, y: Float, point: Point, radius: Float = PointRadius * 3): Boolean {
        val d = distance(Point(x.toInt(), y.toInt()), point)
        return d <= radius
    }

    /**
     * 两点间距离
     * @param point1
     * @param point2
     */
    fun distance(point1: Point, point2: Point): Double {
        return Math.hypot(point1.x.toDouble() - point2.x, point1.y.toDouble() - point2.y)
    }

    /**
     * 获取两点间的夹角，迪卡尔坐标系
     * @param originX 原点X
     * @param originY 原点Y
     * @param pointX
     * @param pointY
     * @return
     */
    fun getDegree(originX: Float, originY: Float, pointX: Float, pointY: Float): Double {
        var degree = 0.0

        val k1 = (originY - originY).toDouble() / (originX * 2 - originX)
        val k2 = (pointY - originY).toDouble() / (pointX - originX)
        val tmpDegree = Math.atan(Math.abs(k1 - k2) / (1 + k1 * k2)) / Math.PI * 180
        val dx = pointX - originX
        val dy = originY - pointY
        if (dx > 0 && dy > 0) {
            //第一象限
            degree = tmpDegree
        } else if (dx < 0 && dy > 0) {
            //第二象限
            degree = 180 - tmpDegree
        } else if (dx < 0 && dy < 0) {
            //第三象限
            degree = 180 + tmpDegree
        } else if (dx > 0 && dy < 0) {
            //第四象限
            degree = 360 - tmpDegree
        } else if (dx == 0f && dy < 0) {
            degree = 90.0
        } else if (dx == 0f && dy > 0) {
            degree = 360.0
        } else if (dy == 0f && dx > 0f) {
            degree = 0.0
        } else if (dy == 0f && dx < 0) {
            degree = 180.0
        }
        return degree
    }

    /**
     * 角度转弧度
     */
    fun getRadian(degree: Double): Double {
        return degree * Math.PI / 180
    }

    /**
     * 弧度转角度
     */
    fun getDegree(radian: Double): Double {
        return radian * 180 / Math.PI
    }
}