package com.deepblue.library.planbmsg.bean

import android.text.TextUtils
import com.alibaba.fastjson.annotation.JSONField
import java.io.Serializable

/**
 * 扫图格式
 * 地图格式
 */
class Map : Serializable {
    var map_info = MapInfo()

    //地图格式专用
    val points = ArrayList<WayPoint>()

    //地图格式专用
    val ranges = ArrayList<MapRange>()

    //删除位点的ID
    val points_del = ArrayList<Int>()

    //删除Range的ID
    val ranges_del = ArrayList<Int>()

    /**
     * 障碍物/橡皮擦临时点
     * 保存时画入png后再从地图数据中清除
     */
    class Brush : Serializable {
        var x = 0F
        var y = 0F
        var radius = 0F
        var color = 0
    }

    var brushes: ArrayList<ArrayList<Brush>>? = null

    //录制路径
    @JSONField(serialize = false)
    val recordPoints = ArrayList<MapPoint>()

    //多边形临时点,贝塞尔曲线临时点(绘制结束后，每两个点之前生成两个控制点)
    @JSONField(serialize = false)
    val polygonPoses = ArrayList<RangePoint>()

    //测量尺的点
    @JSONField(serialize = false)
    val rulers = ArrayList<MapPoint>()


    //定位点
    @JSONField(serialize = false)
    var point: WayPoint? = null

    @JSONField(serialize = false)
    var rangpoints = ArrayList<MapPoint>()

    @JSONField(serialize = false)
    var noEntryRange = ArrayList<MapRange>()


    @JSONField(serialize = false)
    var isObstacleRange = ArrayList<MapRange>()

    //定位导航点
    @JSONField(serialize = false)
    var relocationPos: AngleMapPoint? = null

    @JSONField(serialize = false)
    var resolution = 0.0//缩放地图后实际地图精度

    @JSONField(serialize = false)
    var scale: Float = 1F//缩放地图后缩放比例

    @JSONField(serialize = false)
    var isChecked = false//地图列表中是否选中

    @JSONField(serialize = false)
    var position: Int = 0

    private fun getNewNumber(name: String): Int {
        return try {
            val number = name.substring(1).toInt()
            number + 1
        } catch (e: Exception) {
            1
        }
    }

    private fun findLastWayPoint(): WayPoint? {
        if (points.isEmpty()) {
            return null
        }
        for (i in points.size - 1 downTo 0) {
            if (points[i].real == WayPoint.Real_Point) {
                return points[i]
            }
        }
        return null
    }

    fun getNewWayPointName(): Int {
        val lastWayPoint = findLastWayPoint() ?: return 1
        val name = lastWayPoint.name
        return getNewNumber(name)
    }

    private fun findLastLine(prefix: Char): MapRange? {
        if (ranges.isEmpty()) {
            return null
        }
        for (i in ranges.size - 1 downTo 0) {
            val range = ranges[i]
            if (range.graph_type == MapRange.Graph_Polygon && range.range_type == MapRange.Range_Path) {
                if (range.name.length < 2) {
                    continue
                }
                val firstName = range.name[0]
                val lastName = range.name.substring(1)
                if (firstName == prefix && TextUtils.isDigitsOnly(lastName)) {
                    return range
                }
            }
        }
        return null
    }

    fun getNewLineName(): Int {
        val lastLine = findLastLine('L') ?: return 1
        val name = lastLine.name
        return getNewNumber(name)
    }

    fun getNewRecordName(): Int {
        val lastLine = findLastLine('R') ?: return 1
        val name = lastLine.name
        return getNewNumber(name)
    }

    private fun findLastBezier(): MapRange? {
        if (ranges.isEmpty()) {
            return null
        }
        for (i in ranges.size - 1 downTo 0) {
            val range = ranges[i]
            if (range.graph_type == MapRange.Graph_Bezier) {
                return range
            }
        }
        return null
    }

    fun getNewBezierName(): Int {
        val lastBezier = findLastBezier() ?: return 1
        val name = lastBezier.name
        return getNewNumber(name)
    }

    private fun findLastArea(): MapRange? {
        if (ranges.isEmpty()) {
            return null
        }
        for (i in ranges.size - 1 downTo 0) {
            val range = ranges[i]
            if (range.graph_type == MapRange.Graph_Polygon && range.range_type == MapRange.Range_Area && range.work_type == MapRange.Work_Work) {
                return range
            }
        }
        return null
    }

    fun getNewAreaName(): Int {
        val lastArea = findLastArea() ?: return 1
        val name = lastArea.name
        return getNewNumber(name)
    }
}