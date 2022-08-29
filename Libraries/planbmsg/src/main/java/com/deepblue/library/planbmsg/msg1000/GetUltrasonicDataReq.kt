package com.deepblue.library.planbmsg.msg1000

import com.deepblue.library.planbmsg.Request

class GetUltrasonicDataReq : Request(1006, "查询超声波数据") {

    fun ultrasonic(): String {
        json = Data("ultrasonic")
        return toString()
    }

    fun infrared(): String {
        json = Data("infrared")
        return toString()
    }

    /**
     * 数据区
     * @param type
     * ultrasonic-超声波
     * infrared-红外
     */
    class Data(val type: String)
}