package com.deepblue.library.planbmsg.msg5000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.ListItemTask
import com.deepblue.library.planbmsg.bean.PointInfo

/**
 * 查询默认位点(5007)
 */
class GetMorePointRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        val pointinfo = ArrayList<PointInfo>()
    }
}