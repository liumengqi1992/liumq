package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.ConfigParam
import com.deepblue.library.planbmsg.bean.LevelInfo


class GetLevelRes : Response() {

    companion object {
        fun getJson(getLevelRes: GetLevelRes):Any {
            return getLevelRes.json
        }
    }
}