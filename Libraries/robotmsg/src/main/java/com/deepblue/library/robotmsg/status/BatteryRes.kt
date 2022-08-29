package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 查询机器人被阻挡状态
 */
class BatteryRes : Response() {

    init {
        json = Battery()
    }

    fun getJson(): Battery? {
        return JsonUtils.fromJson(json.toString(), Battery::class.java)
    }

    class Battery {
        var battery_level: Double = 0.0//电池电量,范围[0,100](可缺省)
        var battery_temp: Int = 0//电池温度,单位°(可缺省)
        var charging: Boolean = false//电池是否在充电(可缺省)
        var voltage: Int = 0//电压,单位 V(可缺省)
        var current: Int = 0//电流,单位 A(可缺省)
    }
}