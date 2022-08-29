package com.deepblue.library.robotmsg.control

import com.deepblue.library.robotmsg.Request

/**
 * 执行任务
 */
class StartTaskReq(task_name: String, task_map_name: String, times: Int, default_task: Boolean, property: String? = null) : Request(2003, "robot_control_starttask_req") {

    companion object {
        const val PROPERTY_FAST = "fast"
        const val PROPERTY_NORMAL = "normal"
        const val PROPERTY_SLOW = "slow"
    }

    init {
        json = Data(task_name, task_map_name, times, default_task, property)
    }

    /**
     * 任务
     * @param task_name 任务名字
     *
     */
    class Data(val task_name: String, val task_map_name: String, val times: Int, val default_task: Boolean, val property: String? = null)
}
