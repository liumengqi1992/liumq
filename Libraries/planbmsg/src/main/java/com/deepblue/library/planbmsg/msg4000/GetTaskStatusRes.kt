package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.TaskStatus

/**
 * 获取任务状态(14003)
 */
class GetTaskStatusRes : Response() {

    init {
        json = TaskStatus()
    }

    fun getJson(): TaskStatus? {
        return JsonUtils.fromJson(json.toString(), TaskStatus::class.java)
    }
}