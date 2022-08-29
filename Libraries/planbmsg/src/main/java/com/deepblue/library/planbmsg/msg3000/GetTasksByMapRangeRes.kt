package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.RangeTask

/**
 * 删除地图元素查询关联的任务range（3012）
 */
class GetTasksByMapRangeRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        val tasks = ArrayList<RangeTask>()
    }
}