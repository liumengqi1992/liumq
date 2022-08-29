package com.deepblue.library.planbmsg.msg4000

import com.alibaba.fastjson.annotation.JSONField
import com.deepblue.library.planbmsg.Request
import com.deepblue.library.planbmsg.bean.TaskBasicInfo
import com.deepblue.library.planbmsg.bean.TaskRange

/**
 * @param task_id
 * @param ranges
 * @param reback_id
 * @param work_pattern
 * TaskRange.WORK_PATTERN_NAVIGATION
 * TaskRange.WORK_PATTERN_STANDARD
 * TaskRange.WORK_PATTERN_DRY
 * TaskRange.WORK_PATTERN_WATER
 * TaskRange.WORK_PATTERN_DEEP
 */
class StartTaskReq(task_id: Int, ranges: ArrayList<Range>, reback_id: Int, work_pattern: Int = TaskRange.WORK_PATTERN_NAVIGATION) : Request(4009, "开始任务") {

    init {
        json = Data(task_id, ranges, TaskBasicInfo.TASK_MODE_ONCE, 0, reback_id, work_pattern)
    }

    /**
     * 一次性任务
     */
    fun once(): String {
        val data = json as Data
        data.task_mode = TaskBasicInfo.TASK_MODE_ONCE
        data.executTimes = 0
        return toString()
    }

    /**
     * 有限次任务
     */
    fun times(executTimes: Int): String {
        val data = json as Data
        data.task_mode = TaskBasicInfo.TASK_MODE_LOOP
        data.executTimes = executTimes
        return toString()
    }

    /**
     * 无穷次任务
     */
    fun allways(): String {
        return times(65535)
    }

    /**
     * 数据区
     * @param task_id 任务ID
     * @param ranges
     * @param task_mode 任务模式：TaskBasicInfo.TASK_MODE_ONCE/TaskBasicInfo.TASK_MODE_LOOP
     * @param executTimes 执行次数：仅在循环任务中有效
     * @param reback_id 返回点ID
     * @param work_pattern TaskRange.WORK_PATTERN_NAVIGATION/TaskRange.WORK_PATTERN_STANDARD/TaskRange.WORK_PATTERN_DRY/TaskRange.WORK_PATTERN_WATER/TaskRange.WORK_PATTERN_DEEP
     */
    class Data(val task_id: Int, val ranges: ArrayList<Range>, var task_mode: Int, var executTimes: Int, val reback_id: Int, val work_pattern: Int)

    class Range(val rangeid: Int, val range_type: Int)
}