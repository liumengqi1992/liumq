package com.deepblue.library.robotmsg.control

import com.deepblue.library.robotmsg.Request

/**
 * 录制任务
 */
class RecordTaskReq(operate: String, task_name: String? = null, number: Int) : Request(2014, "robot_control_recordtask_req") {

    companion object {
        const val OPERATE_START = "start"
        const val OPERATE_FINISH = "finish"
    }

    init {
        json = Data(operate, task_name)
        this.number = number
    }

    /**
     * 数据区
     * @param task_name 任务名
     * @param map_name 地图名字
     * @param operate 开始:”start”,结束:”finish”
     */
    class Data(val operate: String, val task_name: String? = null)
}
