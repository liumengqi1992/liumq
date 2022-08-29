package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class PowerReq : Request(2014, "机器人重启/关机") {

    companion object {
        const val SHUTDOWN = 1
        const val REBOOT = 2
    }

    fun shutdown(): String {
        number = SHUTDOWN
        json = Data("shutdown")
        return toString()
    }

    fun reboot(): String {
        number = REBOOT
        json = Data("reboot")
        return toString()
    }

    /**
     * 数据区
     * @param operate 关机:”shutdown”,重启:”reboot”
     */
    class Data(val operate: String)
}
