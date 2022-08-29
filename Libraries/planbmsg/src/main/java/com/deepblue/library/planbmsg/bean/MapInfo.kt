package com.deepblue.library.planbmsg.bean

import android.graphics.Bitmap

class MapInfo : MapInfoBase() {

    //地图名称
    var map_name = ""

    //最小点(左上角角)
    var min_pos = MapPoint()

    //最大点(右下角)
    var max_pos = MapPoint()

    //分辨率
    var resolution = 1.0
    //扫图格式仅有以上数据

    //地图类型
    var map_type = ""

    //ROS版本
    var ros_version = ""

    //激光类型
    var laser_type = ""

    //激光高度
    var laser_height = 0

    //扫图模块
    var scan_moudle = ""

    var bitmap: Bitmap? = null
}