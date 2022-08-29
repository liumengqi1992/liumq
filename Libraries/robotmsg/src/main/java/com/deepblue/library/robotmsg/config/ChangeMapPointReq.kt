package com.deepblue.library.robotmsg.config

import com.deepblue.library.robotmsg.Request

/**
 * 映射切换地图点
 */
class ChangeMapPointReq(map_points: ArrayList<MapPoint>) : Request(3010, "robot_config_changemappoint_req") {

    init {
        json = Data(map_points)
    }

    /**
     * 数据区
     */
    class Data(val map_points: ArrayList<MapPoint>)

    class MapPoint(val from_map: String, val from_point: String, val to_map: String, val to_point: String)
}
