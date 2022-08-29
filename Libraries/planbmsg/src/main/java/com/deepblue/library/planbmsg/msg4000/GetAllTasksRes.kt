package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.ListItemTask

/**
 * 查询所有任务(14005)
 */
class GetAllTasksRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        val tasks = ArrayList<ListItemTask>()
    }
}