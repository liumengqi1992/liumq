package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request

class ChangeNaviMapReq(map_id: Int, use_map_point: Boolean = false) : Request(3008, "切换导航地图") {

    init {
        json = Data(map_id, use_map_point)
    }

    /**
     * 数据区
     * @param map_id 地图ID
     * @param use_map_point 是否使用映射地图点,如果使用映射点,
     * 那么系统会根据当前地图中的切换地图点找到目标地图中的切换地图映射点,
     * 并作为机器人在目标地图中的初始位置。
     */
    class Data(val map_id: Int, val use_map_point: Boolean)
}
