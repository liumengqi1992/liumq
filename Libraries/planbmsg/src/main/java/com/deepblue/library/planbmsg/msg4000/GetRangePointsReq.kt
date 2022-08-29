package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.Request

class GetRangePointsReq(index: Int = 0) : Request(4006, "查询区域内的实际运动路径") {

    init {
        number = index
    }

    fun map(id: Int): String {
        json = Data(id,0)
        return toString()
    }

    fun task(id: Int,task_id: Int): String {
        json = Data(id,task_id)
        return toString()
    }

    /**
     * 数据区
     * @param id 区域id
     * @param type 类型。
     * 1-查询地图区域
     * 2-查询任务区域
     */
    class Data(val id: Int,val task_id: Int)

}