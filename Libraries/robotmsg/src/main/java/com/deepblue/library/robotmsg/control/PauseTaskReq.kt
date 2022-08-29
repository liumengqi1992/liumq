package com.deepblue.library.robotmsg.control

import com.deepblue.library.robotmsg.Request

/**
 * 暂停当前任务
 */
class PauseTaskReq : Request(2004, "robot_control_pausetask_req")