package com.deepblue.library.robotmsg.control

import com.deepblue.library.robotmsg.Request

/**
 * 下载任务
 * @param task_name 任务名字
 * @param task_map_name 任务所在的地图名字
 */
class DownloadTaskReq(task_name: String,
                      task_map_name: String, type: Int) : Request(2011, "robot_control_downloadtask_req") {

    init {
        number = type
        json = Data(task_name, task_map_name)
    }

    class Data(val task_name: String, val task_map_name: String)
}
