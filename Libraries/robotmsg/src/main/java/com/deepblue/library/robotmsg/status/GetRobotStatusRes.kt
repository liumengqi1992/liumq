package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 查询机器人状态
 */
class GetRobotStatusRes : Response() {

    companion object {
        const val STATUS_OFFLINE = 2019//离线状态
        const val STATUS_IDLE = 0//待命状态
        const val STATUS_TASK = 1//运行任务状态
        const val STATUS_MANUAL_CHARGE = 2//手动充电状态
        const val STATUS_SLAM = 3//创建地图状态
        const val STATUS_ERROR = 4//故障状态
        const val STATUS_RELOCATION = 5//重定位状态
        const val STATUS_AUTO_CHARGE = 6//自动充电状态
        const val STATUS_CREATE_TASK = 7//创建任务状态
        const val STATUS_RECORD_PATH = 8//录制路径状态
    }

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        /**
         * 机器人状态
         * 0:待命状态
         * 1:运行任务状态
         * 2:手动充电状态
         * 3:创建地图状态
         * 4:故障状态
         * 5:自动充电状态
         */
        var status: Int = 0
    }
}