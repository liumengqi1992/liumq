package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 获取设备信息
 */
class GetDeviceInfoRes : Response() {

    init {
        json = DeviceInfo()
    }

    fun getJson(): DeviceInfo? {
        return JsonUtils.fromJson(json.toString(), DeviceInfo::class.java)
    }

    class DeviceInfo {
        var device_name: String = ""
        var latest_user: String = ""
        var latest_used_time: Long = 0
        var model: String = ""
        var device_id: String = ""
        var network_status: Boolean = false
    }
}