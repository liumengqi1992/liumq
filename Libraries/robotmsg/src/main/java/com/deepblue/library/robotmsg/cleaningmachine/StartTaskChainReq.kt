package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.Request
import com.deepblue.library.robotmsg.bean.TaskChains

/**
 * 执行任务链
 */
class StartTaskChainReq(task_chain: TaskChains) : Request(5005, "robot_cleaningmachine_starttaskchain_req") {

    init {
        json = task_chain
    }
}
