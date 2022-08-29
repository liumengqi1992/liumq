package com.deepblue.library.planbmsg.bean

/**
 * 扫图格式
 * 地图格式
 * 保存地图用
 */
class MapEdit {
    var map_info: MapInfoBase? = null
    //地图格式专用
    val points = ArrayList<WayPoint>()
    //地图格式专用
    val ranges = ArrayList<MapRange>()
    //删除位点的ID
    val points_del = ArrayList<Int>()
    //删除Range的ID
    val ranges_del = ArrayList<Int>()
}