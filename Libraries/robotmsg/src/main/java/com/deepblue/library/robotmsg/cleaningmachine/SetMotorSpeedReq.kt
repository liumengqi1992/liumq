package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.Request

/**
 * 设置吸尘强度
 */
class SetMotorSpeedReq(value: Int) : Request(5022, "robot_cleaningmachine_setmotorspeed_req") {

    companion object {
        const val SPEED_CLOSE = 0
        const val SPEED_LOW = 1
        const val SPEED_MIDDLE = 2
        const val SPEED_HIGH = 3
    }

    init {
        json = Data(value)
    }

    /**
     * 数据区
     * @param value 0:关闭,1:弱,2:中;3:强
     */
    class Data(val value: Int)
}
