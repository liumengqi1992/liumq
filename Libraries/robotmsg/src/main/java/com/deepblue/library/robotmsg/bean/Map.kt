package com.deepblue.library.robotmsg.bean

import android.text.TextUtils
import com.alibaba.fastjson.annotation.JSONField
import kotlin.collections.ArrayList

/**
 * 地图格式
 */
class Map {

    /**
     * 障碍物/橡皮擦临时点
     * 保存时画入png后再从地图数据中清除
     */
    class Brush {
        var x = 0F
        var y = 0F
        var radius = 0F
        var color = 0
    }

    //    var brushes: ArrayList<Brush>? = null
    var brushes: ArrayList<ArrayList<Brush>>? = null

    //测量尺的点
    @JSONField(serialize = false)
    val rulers = ArrayList<Pos>()

    //绘制路径线时的起点
    @JSONField(serialize = false)
    var lineStartWayPoint: WayPoint? = null

    @JSONField(serialize = false)
    val polygonPoses = ArrayList<Pos>()

    //定位导航点
    @JSONField(serialize = false)
    var relocationPos: WayPoint? = null

    @JSONField(serialize = false)
    var resolution: Double = 0.0//缩放地图后实际地图精度
    @JSONField(serialize = false)
    var scale: Float = 1F//缩放地图后缩放比例
    //以上为绘制所需方法

    var header = Header()
    var element: Element? = null
    var data: String = ""

    class Header {
        var map_name: String = ""//地图名字(ID)
        var map_type: String = ""//地图类型
        var max_pos = Pos()//地图最大点
        var min_pos = Pos()//地图最小点
        var resolution: Double = 0.0//地图精度
        var encode: String = ""//编码格式
    }

    class Element {
        var waypoints = ArrayList<WayPoint>()
        var lines = Lines()
        var areas = Areas()
    }

    class Pos {
        var x: Double = 0.0
        var y: Double = 0.0
        var angle: Double? = null

        @JSONField(serialize = false)
        var isFocused = false
        @JSONField(serialize = false)
        var name = ""
        @JSONField(serialize = false)
        var label = ""
            get() {
                if (TextUtils.isEmpty(field)) {
                    return name
                }
                return field
            }
    }

    class WayPoint {
        var name: String = ""//ID
        var label: String = ""//名字(显示用)
            get() {
                if (TextUtils.isEmpty(field)) {
                    return name
                }
                return field
            }
        var type = ArrayList<String>()
        var pos = Pos()

        @JSONField(serialize = false)
        var isFocused = false

        fun init() {
            type.add("nav_point")
        }
    }

    class Lines {
        var bezier_lines = ArrayList<BezierLine>()
        var forbidden_lines = ArrayList<ForbiddenLine>()
    }

    class BezierLine {
        var name: String = ""//ID
        var label: String = ""//名字(显示用)
            get() {
                if (TextUtils.isEmpty(field)) {
                    return name
                }
                return field
            }
        var type: String = "normal_line"
        var start_waypoint: String = ""
        var end_waypoint: String = ""
        var control_points = ArrayList<Pos>()

        @JSONField(serialize = false)
        var isFocused = false
    }

    class ForbiddenLine {
        var name: String = ""//ID
        var label: String = ""//名字(显示用)
            get() {
                if (TextUtils.isEmpty(field)) {
                    return name
                }
                return field
            }
        var type: String = "forbidden_line"
        var start_point = Pos()
        var end_point = Pos()

        @JSONField(serialize = false)
        var isFocused = false
    }

    class Areas {
        var polygons = ArrayList<Polygon>()
        var circles = ArrayList<Circle>()
    }

    class Polygon {
        var points = ArrayList<Pos>()
        var name: String = ""//ID
        var label: String = ""//名字(显示用)
            get() {
                if (TextUtils.isEmpty(field)) {
                    return name
                }
                return field
            }
        var type: String = ""

        @JSONField(serialize = false)
        var isFocused = false
    }

    class Circle {
        var name: String = ""//ID
        var label: String = ""//名字(显示用)
            get() {
                if (TextUtils.isEmpty(field)) {
                    return name
                }
                return field
            }
        var type: String = ""
        var point = Pos()
        var radius: Double = 0.0

        @JSONField(serialize = false)
        var isFocused = false
    }
}