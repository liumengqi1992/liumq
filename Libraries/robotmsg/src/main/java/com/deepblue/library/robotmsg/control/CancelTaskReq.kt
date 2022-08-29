package com.deepblue.library.robotmsg.control

import com.deepblue.library.robotmsg.Request

/**
 * 取消当前任务
 */
class CancelTaskReq : Request(2006, "robot_control_canceltask_req")