package com.deepblue.library.planbmsg.bean

import com.alibaba.fastjson.annotation.JSONField
import java.io.Serializable

open class MapRange : Serializable {

    companion object {
        const val Range_Area = 1
        const val Range_Path = 2

        const val Work_Work = 1
        const val Work_Forbidden = 2
        const val Work_Carpet = 3
        const val Work_Display = 4
        const val Work_Deceleration = 5
        const val Work_Slope = 6

        const val Graph_Polygon = 1
        const val Graph_Line = 1
        const val Graph_Circle = 2
        const val Graph_Bezier = 3
        const val Graph_Rect = 4
    }

    //路径/区域ID
    var range_id = 0

    //地图ID
    var map_id = 0

    //路径/区域名称
    var name = ""

    //类型.1-区域;2-路径
    var range_type = 0

    //工作类型
    var work_type = 0

    //区域形状,1-多边形/折线(不包括矩形),2-椭圆/圆,3-贝塞尔曲线,4-矩形
    var graph_type = 0

    //点位ID序列
    var point_id = ArrayList<Int>()

    //点位ID序列
    var point_info = ArrayList<RangePoint>()

    @JSONField(serialize = false)
    var isFocused = false

    //记录是否修改过
    var isChanged = false

    //是否开口
    var isopen=false

    //查询区域内的实际运动路径（4006）
    @JSONField(serialize = false)
    var points = ArrayList<MapPoint>()
}