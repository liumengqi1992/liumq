package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 查询机器人任务状态
 */
class TaskRes : Response() {

    companion object {
        const val STATE_INIT = "TaskInitState"//任务初始状态
        const val STATE_PAUSED = "TaskPausedState"//任务暂停状态
        const val STATE_ACTIVE = "TaskActiveState"//任务激活状态
        const val STATE_CANCELLED = "TaskCancelledState"//任务取消状态
        const val STATE_FINISHED = "TaskFinishedState"//任务完成状态
        const val STATE_FAILED = "TaskFailedState"//任务完成状态
    }

    init {
        json = Task()
    }

    fun getTask(): Task? {
        return JsonUtils.fromJson(json.toString(), Task::class.java)
    }

    class Task {
        var task_id: Int = 0//任务 ID
        var task_state: String = STATE_INIT//任务状态
        var finished_step: Int = 0//已经完成步骤数目
        var total_step: Int = 0//总的步骤数目
        var waypoints = ArrayList<String>()//任务中经过的所有位点
    }
}