package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response
import com.deepblue.library.robotmsg.bean.Map

/**
 * 查询运行信息
 */
class GetScanRes : Response() {

    init {
        json = Points()
    }

    fun getJson(): Points? {
        return JsonUtils.fromJson(json.toString(), Points::class.java)
    }

    class Points {
        var scan_points = ArrayList<Map.Pos>()
    }

//    class Point(val x: Double, val y: Double)
}