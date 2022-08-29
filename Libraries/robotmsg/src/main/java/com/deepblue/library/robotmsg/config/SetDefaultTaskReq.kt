package com.deepblue.library.robotmsg.config

import com.deepblue.library.robotmsg.Request

/**
 * 设置默认任务
 */
class SetDefaultTaskReq(task_name: String, task_map_name: String) : Request(3006, "robot_config_setdefaulttask_req") {

    init {
        json = Data(task_name, task_map_name)
    }

    /**
     * 数据区
     * @param task_name 任务名字
     * @param task_map_name 地图名字
     */
    class Data(val task_name: String, val task_map_name: String)
}
