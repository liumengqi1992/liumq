package com.deepblue.library.robotmsg.task

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

class GetcurrenttaskRes : Response() {
    init {
        json = PlayTaskReq.PlayTask()
    }

    fun getJson(): PlayTaskReq.PlayTask? {
        return JsonUtils.fromJson(json.toString(), PlayTaskReq.PlayTask::class.java)
    }
}