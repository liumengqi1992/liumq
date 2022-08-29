package com.deepblue.library.robotmsg.app

import com.deepblue.library.robotmsg.Request

/**
 * 修改任务Label
 * @param map_name
 * @param task_name
 * @param new_label
 */
class ModifyTaskLabelReq(map_name: String,
                         task_name: String, new_label: String, index: Int) : Request(5000, "robot_app_modifytasklabel_req") {

    init {
        number = index
        json = Data(map_name, task_name, new_label)
    }

    class Data(val map_name: String, val task_name: String, val new_label: String)
}
