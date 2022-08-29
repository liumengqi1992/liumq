package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.TaskInfo

/**
 * 查询单个任务(14004)
 */
class GetTaskRes : Response() {

    init {
        json = TaskInfo()
    }

    fun getJson(): TaskInfo? {
        return JsonUtils.fromJson(json.toString(), TaskInfo::class.java)
    }
}