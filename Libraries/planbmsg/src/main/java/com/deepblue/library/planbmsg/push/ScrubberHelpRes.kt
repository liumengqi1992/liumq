package com.deepblue.library.planbmsg.push

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.ScrubberHelp

/**
 * 推送洗地机求助(24005)
 */
class ScrubberHelpRes : Response() {

    init {
        json = ScrubberHelp()
    }

    fun getJson(): ScrubberHelp? {
        return JsonUtils.fromJson(json.toString(), ScrubberHelp::class.java)
    }
}