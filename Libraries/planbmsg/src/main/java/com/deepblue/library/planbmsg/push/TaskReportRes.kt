package com.deepblue.library.planbmsg.push

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.CleanProInfo
import com.deepblue.library.planbmsg.bean.ScrubberReport

class TaskReportRes  : Response() {

    init {
        json = ScrubberReport()
    }

    fun getJson(): ScrubberReport? {
        val scrubberReport = JsonUtils.fromJson(json.toString(), ScrubberReport::class.java) ?: return null
        return scrubberReport
    }
}