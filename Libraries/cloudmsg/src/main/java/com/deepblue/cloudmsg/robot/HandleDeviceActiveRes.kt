package com.deepblue.cloudmsg.robot

import com.deepblue.cloudmsg.Response
import com.deepblue.cloudmsg.bean.Device
import com.deepblue.library.planbmsg.JsonUtils

/**
 * 设备激活
 */
class HandleDeviceActiveRes: Response() {

    init {
        data = Device()
    }

    fun getData(): Device? {
        return JsonUtils.fromJson(data.toString(), Device::class.java)
    }
}