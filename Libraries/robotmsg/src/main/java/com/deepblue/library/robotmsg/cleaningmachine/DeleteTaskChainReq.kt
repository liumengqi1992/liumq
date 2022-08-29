package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.Request

/**
 * 删除任务链
 */
class DeleteTaskChainReq(map_name: String, task_chain_name: String) : Request(5004, "robot_cleaningmachine_deletetaskchain_req") {

    init {
        json = Data(map_name, task_chain_name)
    }

    /**
     * 数据区
     * @param map_name 地图名字
     * @param task_chain_name 任务链名字
     */
    class Data(val map_name: String, val task_chain_name: String)
}
