package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.Request

/**
 * 查询机器人电池状态
 */
class BatteryReq : Request(1004, "robot_status_battery_req")
