package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 查询机器人上所有的任务
 */
class GetAllTasksRes : Response() {

    init {
        json = Tasks()
    }

    fun getJson(): Tasks? {
        return JsonUtils.fromJson(json.toString(), Tasks::class.java)
    }

    class Tasks {
        var tasks = ArrayList<String>()//获取机器人上所有的任务
    }
}