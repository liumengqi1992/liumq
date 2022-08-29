package com.deepblue.library.robotmsg.task

import com.deepblue.library.robotmsg.Request

class AutochargeReq(operate: String, charge_poin: String) : Request(2013, "robot_control_autocharge_req") {
    init {
        json = Data(operate, charge_poin)
    }

    class Data(val operate: String, val charge_poin: String)
}
