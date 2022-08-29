package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 获取所有任务链
 */
class GetAllTaskChainRes : Response() {

    init {
        json = TaskChains()
    }

    fun getJson(): TaskChains? {
        return JsonUtils.fromJson(json.toString(), TaskChains::class.java)
    }

    class TaskChains {
        var task_chains = ArrayList<String>()
    }
}