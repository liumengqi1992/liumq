package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.MapPoint

/**
 * 查询任务路径(14007)
 */
@Deprecated("接口废弃，使用4006即可")
class GetTaskPointsRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        val points = ArrayList<MapPoint>()
    }
}