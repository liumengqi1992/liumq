package com.deepblue.cloudmsg.ota

import com.deepblue.cloudmsg.Response
import com.deepblue.cloudmsg.bean.OTAVersion
import com.deepblue.cloudmsg.bean.User
import com.deepblue.library.planbmsg.JsonUtils

/**
 * 通过设备信息获取最新版本信息
 */
class GetLastVersionByDeviceInfoRes: Response() {

    init {
        data = OTAVersion()
    }

    fun getData(): OTAVersion? {
        return JsonUtils.fromJson(data.toString(), OTAVersion::class.java)
    }
}