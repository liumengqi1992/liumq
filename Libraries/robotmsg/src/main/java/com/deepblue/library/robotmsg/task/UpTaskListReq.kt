package com.deepblue.library.robotmsg.task

import com.deepblue.library.robotmsg.Request
import com.deepblue.library.robotmsg.bean.TaskList


class UpTaskListReq(taskList: TaskList) : Request(5011, "robot_app_uploadtaskchain_req") {

    init {
        json = taskList
    }
}