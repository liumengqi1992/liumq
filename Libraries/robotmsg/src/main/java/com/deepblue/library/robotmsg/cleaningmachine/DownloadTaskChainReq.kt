package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.Request

/**
 * 下载任务链
 */
class DownloadTaskChainReq(map_name: String, task_chain_name: String, last_task_chain: Boolean) : Request(5010, "robot_cleaningmachine_downloadtaskchain_req") {

    init {
        json = Data(map_name, task_chain_name, last_task_chain)
    }

    /**
     * 数据区
     * @param map_name 地图名字
     * @param task_chain_name 任务链名字
     * @param last_task_chain 是否下载上一次的任务链 true:下载上一次任务链;false:忽略这个字段
     */
    class Data(val map_name: String, val task_chain_name: String, val last_task_chain: Boolean)
}
