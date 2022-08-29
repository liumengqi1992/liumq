package com.deepblue.library.planbmsg.msg1000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.WorkingData

/**
 * 查询机器人运行数据(11005)
 */
@Deprecated("接口废弃")
class GetWorkingDataRes : Response() {

    init {
        json = WorkingData()
    }

    fun getJson(): WorkingData? {
        return JsonUtils.fromJson(json.toString(), WorkingData::class.java)
    }
}