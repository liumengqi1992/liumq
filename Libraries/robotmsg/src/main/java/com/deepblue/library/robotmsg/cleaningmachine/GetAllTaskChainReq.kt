package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.Request

/**
 * 获取所有任务链
 */
class GetAllTaskChainReq(map_name: String) : Request(5009, "robot_cleaningmachine_getalltaskchain_req") {

    init {
        json = Data(map_name)
    }

    /**
     * 数据区
     * @param map_name 地图名字
     */
    class Data(val map_name: String)
}
