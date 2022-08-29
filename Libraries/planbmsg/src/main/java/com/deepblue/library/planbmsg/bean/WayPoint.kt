package com.deepblue.library.planbmsg.bean

import com.alibaba.fastjson.annotation.JSONField

class WayPoint : AngleMapPoint() {

    companion object {
        //充电点C
        const val Type_Charge = "C"
        //导航点P
        const val Type_Navigation = "P"
        //重定位点D
        const val Type_Relocation = "D"
        //电梯点L
        const val Type_Lift = "L"
        //前台点G
        const val Type_Front_Desk = "G"
        //房间点R
        const val Type_Room = "R"
        //加水点E
        const val Type_EWater = "E"
        //排水点H
        const val Type_HWater = "H"
        //取餐点T
        const val Type_Food = "T"
        //桌位点Z
        const val Type_Table = "Z"

        //真实点
        const val Real_Point = 1
        //临时点
        const val Temp_Point = 0
    }

    //点位ID
    var point_id = 0
    //点位名称
    var name = ""
    //点位类型
    val type = ArrayList<String>()
    //坐标类型。1-gis;2-点云
    var coordinates_type = 2
    //是否是真实点；1-真实点；0-临时点
    var real = Temp_Point
    //地图ID
    var map_id = 0

    @JSONField(serialize = false)
    var isFocused = false

    //记录是否修改过
    var isChanged = false

    //与点击点的距离
    @JSONField(serialize = false)
    var distanceClick = Double.MAX_VALUE
}