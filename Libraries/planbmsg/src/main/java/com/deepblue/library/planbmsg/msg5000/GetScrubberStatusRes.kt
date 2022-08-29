package com.deepblue.library.planbmsg.msg5000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.ScrubberReport
import com.deepblue.library.planbmsg.bean.ScrubberStatus

/**
 * 查询洗地机工作状态(15003)
 */
class GetScrubberStatusRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        var clean_capacity=0;
        var dirty_capacity=0;
    }
}