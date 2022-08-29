package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.Request

class ChangeTaskStatusReq : Request(4002, "任务状态修改") {

    companion object {
        const val DELETE = 1
        const val PAUSE = 2
        const val RESUME = 3
        const val STOP = 5
    }

    fun delete(task_id: Int): String {
        number = DELETE
        json = Data("delete", task_id)
        return toString()
    }

    fun pause(task_id: Int): String {
        number = PAUSE
        json = Data("pause", task_id)
        return toString()
    }

    fun resume(task_id: Int): String {
        number = RESUME
        json = Data("continue", task_id)
        return toString()
    }

    fun stop(task_id: Int): String {
        number = STOP
        json = Data("cancel", task_id)
        return toString()
    }

    fun cancel_lpower(task_id: Int): String {
        number = STOP
        json = Data("cancel_lpower", task_id)
        return toString()
    }

    fun cancel_people(task_id: Int): String {
        number = STOP
        json = Data("cancel_people", task_id)
        return toString()
    }

    fun cancel_error(task_id: Int): String {
        number = STOP
        json = Data("cancel_fault", task_id)
        return toString()
    }

    fun cancel_stop(task_id: Int): String {
        number = STOP
        json = Data("cancel_emerbutton", task_id)
        return toString()
    }

    /**
     * 数据区
     * @param task_id 任务ID
     * @param status 任务状态
     */
    class Data(val status: String, val task_id: Int? = null)
}