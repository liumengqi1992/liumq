package com.deepblue.library.planbmsg.msg1000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.ScanPoints

/**
 * 查询激光数据(11003)
 */
class GetScanRes : Response() {

    init {
        json = ScanPoints()
    }

    fun getJson(): ScanPoints? {
        return JsonUtils.fromJson(json.toString(), ScanPoints::class.java)
    }
}