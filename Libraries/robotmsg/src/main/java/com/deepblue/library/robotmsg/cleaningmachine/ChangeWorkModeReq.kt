package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.Request

/**
 * 切换工作模式
 */
class ChangeWorkModeReq(work_mode: String) : Request(5015, "robot_cleaningmachine_changeworkmode_req") {

    companion object {
        const val WORK_MODE_AUTO = "auto"
        const val WORK_MODE_MANUAL = "manual"
    }

    init {
        json = Data(work_mode)
    }

    /**
     * 数据区
     * @param work_mode auto :自动模式,manual:手动模式
     */
    class Data(val work_mode: String)
}
