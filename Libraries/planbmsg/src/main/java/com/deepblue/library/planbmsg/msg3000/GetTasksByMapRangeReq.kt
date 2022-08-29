package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request

class GetTasksByMapRangeReq(val map_id: Int, val range_id: Int) : Request(3012, "删除地图元素查询关联的任务range") {

    companion object {
        const val TYPE_POINT = 0
        const val TYPE_RANGE = 1
    }

    fun point(): String {
        number = TYPE_POINT
        json = Data(map_id, range_id, number)
        return toString()
    }

    fun range(): String {
        number = TYPE_RANGE
        json = Data(map_id, range_id, number)
        return toString()
    }

    /**
     * 数据区
     * @param map_id 地图ID
     * @param range_id 地图区域ID
     * @param type 0:点位 1：区域
     */
    class Data(val map_id: Int, val range_id: Int, val type: Int)
}