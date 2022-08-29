package com.deepblue.library.robotmsg.task

import android.text.TextUtils
import com.deepblue.library.robotmsg.Request

class PlayTaskReq(playtask: PlayTask) : Request(5005, "robot_app_starttaskchain_req") {
    init {
        json = playtask
    }

    class PlayTask() {
        var map_name: String = ""
        var task_chain_name: String = ""
        var times: Int = 1
        var task_chain = ArrayList<TaskChain>()
    }

    class TaskChain() {
        var task_label:String=""
            get() {
                if (TextUtils.isEmpty(field)) {
                    return task_name
                }
                return field
            }
        var task_name: String = ""
        var run: Boolean = false
        var task_type: Int = 0
    }
}