package com.deepblue.library.robotmsg.control

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.Task

/**
 * 下载任务
 */
class DownloadTaskRes : Response() {

    init {
        json = Task()
    }

    fun getJson(): Task? {
        return JsonUtils.fromJson(json.toString(), Task::class.java)
    }
}