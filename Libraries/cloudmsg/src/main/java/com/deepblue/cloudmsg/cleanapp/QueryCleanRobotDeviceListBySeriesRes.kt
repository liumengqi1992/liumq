package com.deepblue.cloudmsg.cleanapp

import com.deepblue.cloudmsg.Response
import com.deepblue.cloudmsg.bean.Device
import com.deepblue.library.planbmsg.JsonUtils

/**
 * 根据系列查询商户设备列表
 */
class QueryCleanRobotDeviceListBySeriesRes: Response() {

    init {
        data = Data()
    }

    fun getData(): Data? {
        return JsonUtils.fromJson(data.toString(), Data::class.java)
    }

    class Data {
        var total = 0
        var pageNum = 0
        var pageSize = 0
        var pages = 0
        val rows = ArrayList<Device>()
    }
}