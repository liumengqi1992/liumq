package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.Request

/**
 * 去充电点/定位点
 */
class SendGoalReq(waypoint: String) : Request(5021, "robot_cleaningmachine_sendgoal_req") {

    companion object {
        const val CHARGE_POINT = "charge_point"
        const val LOCATION_POINT = "location_point"
    }

    init {
        json = Data(waypoint)
    }

    /**
     * 数据区
     * @param waypoint charge_point:充电点;location_point:定位点
     */
    class Data(val waypoint: String)
}
