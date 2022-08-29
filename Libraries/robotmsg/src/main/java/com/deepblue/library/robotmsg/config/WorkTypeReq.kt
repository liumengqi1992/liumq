package com.deepblue.library.robotmsg.config

import com.deepblue.library.robotmsg.Request

/**
 * 切换导航地图
 */
class WorkTypeReq(work_mode: String) : Request(5015, "robot_app_changeworkmode_req") {

    init {
        json = Data(work_mode)
    }

    class Data(val work_mode: String)
}
