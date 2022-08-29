package com.deepblue.library.planbmsg.msg1000

import com.deepblue.library.planbmsg.Request
import org.json.JSONObject

class GetHardwareReq (ss: JSONObject) : Request(1010, "查询机器人上硬件"){
    init {
        json=ss;
    }

}
