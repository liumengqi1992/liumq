package com.deepblue.library.robotmsg.task

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.TaskListChains

/**
 * 下载任务链
 */
class DownloadTaskChainRes : Response() {

    init {
        json = TaskListChains()
    }

    fun getJson(): TaskListChains? {
        return JsonUtils.fromJson(json.toString(), TaskListChains::class.java)
    }
}