package com.deepblue.library.robotmsg.cleaningmachine

import com.deepblue.library.robotmsg.Request
import com.deepblue.library.robotmsg.bean.TaskChains

/**
 * 上传任务链
 */
class UploadTaskChainReq(task_chain: TaskChains) : Request(5011, "robot_cleaningmachine_uploadtaskchain_req") {

    init {
        json = task_chain
    }
}
