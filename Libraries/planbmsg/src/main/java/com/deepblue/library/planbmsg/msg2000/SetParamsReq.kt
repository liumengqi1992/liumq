package com.deepblue.library.planbmsg.msg2000

import android.util.Base64
import com.deepblue.library.planbmsg.Request
import com.deepblue.library.planbmsg.bean.ConfigParam

class SetParamsReq(filename: String, data: String) : Request(2000, "配置导航平台参数") {

    init {
        json = ConfigParam()
        (json as ConfigParam).filename = filename
        (json as ConfigParam).data = Base64.encodeToString(data.toByteArray(), ConfigParam.FLAGS)
    }
}