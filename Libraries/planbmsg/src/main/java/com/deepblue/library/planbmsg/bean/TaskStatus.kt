package com.deepblue.library.planbmsg.bean

/**
 * 获取任务状态(4003)
 */
class TaskStatus {

    companion object {
        //未开始
        const val IDLE = 1
        //正在执行
        const val WORKING = 2
        //任务异常
        const val ERROR = 3
        //已完成
        const val DONE = 4
        //已取消
        const val CANCELED = 5
        //取消中
        const val CANCELING = 6
        //暂停
        const val PAUSE = 7
        //继续完成任务
        const val REWORK = 8
    }

    //任务状态
    var status = 0
    //工作时间
    var work_duration = 0.0
    //剩余时间=任务链预计完成时间-工作时间
    var remaining_time = 0.0
    //任务进度百分比。0-100
    var task_progress = 0.0
}