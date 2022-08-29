package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.Route

/**
 * 获取任务链路径
 */
class GetTaskChainPathRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        var data = ArrayList<Route>()
    }
}