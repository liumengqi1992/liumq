package com.deepblue.library.planbmsg.msg5000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.ScrubberReport

/**
 * 获取洗地机任务报告(15002)
 */
class GetScrubberReportsRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        val reports = ArrayList<ScrubberReport>()
    }
}