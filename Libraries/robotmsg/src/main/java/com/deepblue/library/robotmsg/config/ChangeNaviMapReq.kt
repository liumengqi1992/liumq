package com.deepblue.library.robotmsg.config

import com.deepblue.library.robotmsg.Request

/**
 * 切换导航地图
 */
class ChangeNaviMapReq(map_name: String, use_map_point: Boolean = false) : Request(3005, "robot_config_changenavimap_req") {

    init {
        json = Data(map_name, use_map_point)
    }

    /**
     * 数据区
     * @param map_name 地图名
     * @param use_map_point 是否使用映射地图点,如果使用映射点,
     * 那么系统会根据当前地图中的切换地图点找到目标地图中的切换地图映射点,
     * 并作为机器人在目标地图中的初始位置。具体请参考发送指令(3010)
     */
    class Data(val map_name: String, val use_map_point: Boolean)
}
