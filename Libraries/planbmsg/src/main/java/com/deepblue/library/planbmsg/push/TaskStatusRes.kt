package com.deepblue.library.planbmsg.push

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.ScrubberReport
import com.deepblue.library.planbmsg.bean.TaskState
import com.deepblue.library.planbmsg.bean.TaskStatus

/**
 * 推送任务状态变化(24006)
 */
class TaskStatusRes : Response() {
    init {
        json = TaskState()
    }

    fun getJson(): TaskState? {
        val taskState = JsonUtils.fromJson(json.toString(), TaskState::class.java) ?: return null
        return taskState
    }
}