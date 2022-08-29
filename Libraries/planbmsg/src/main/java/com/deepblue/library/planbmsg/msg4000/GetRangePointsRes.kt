package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.MapPoint

/**
 * 查询区域内的实际运动路径(14006)
 */
class GetRangePointsRes : Response() {

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