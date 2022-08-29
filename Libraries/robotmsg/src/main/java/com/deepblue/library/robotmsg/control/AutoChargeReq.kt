package com.deepblue.library.robotmsg.control

import com.deepblue.library.robotmsg.Request

/**
 * 自主充电
 */
class AutoChargeReq(operate: String, charge_point: String? = null) : Request(2013, "robot_control_autocharge_req") {

    companion object {
        const val OPERATE_START = "start"
        const val OPERATE_FINISH = "finish"
    }

    init {
        json = Data(operate, charge_point)
    }

    /**
     * 数据区
     * @param operate 开始:”start”,结束:”finish”
     * @param charge_point 充电站点名字
     */
    class Data(val operate: String, val charge_point: String? = null)
}
