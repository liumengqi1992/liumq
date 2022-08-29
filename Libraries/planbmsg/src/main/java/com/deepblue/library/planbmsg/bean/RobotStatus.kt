package com.deepblue.library.planbmsg.bean

/**
 * 查询机器人状态（1004）
 */
class RobotStatus {

    companion object {
        const val STATUS_OFFLINE = 2019//离线
        const val STATUS_IDLE = 3//待命
        const val STATUS_TASK = 4//执行任务
        const val STATUS_SLAM = 5//新建地图
        const val STATUS_RECORD_PATH = 6//录制路径
        const val STATUS_RELOCATION = 7//重定位
        const val STATUS_AUTO_CHARGE = 8//自动充电
        const val STATUS_MANUAL_CHARGE = 9//手动充电
        const val STATUS_ERROR = 10//故障
        const val STATUS_EMERGENCY = 11//急停
        const val STATUS_NAVIGATING = 20//自由导航
        const val STATUS_BREAK = 26//中断状态
        const val STATUS_GO_BREAK = 27//去中断点状态
    }

    var status = STATUS_IDLE
        get() {
            //本体未设初始值时，会返回该值
            if (field == 104) {
                return STATUS_IDLE
            }
            return field
        }
}