package com.deepblue.library.robotmsg.config

import com.deepblue.library.robotmsg.Request

/**
 * 重启/关机/休眠机器人
 */
class RestartRobotReq(power: String) : Request(3007, "robot_config_restartrobot_req") {

    companion object {
        const val POWER_REBOOT = "reboot"
        const val POWER_SHUTDOWN = "shutdown"
        const val POWER_SLEEP = "sleep"
    }

    init {
        json = Data(power)
    }

    /**
     * 数据区
     * @param power 操作指令名字
     * 重启:“reboot”;
     * 关机:”shutdown”;
     * 休眠:”sleep”
     */
    class Data(val power: String)
}
