package com.deepblue.library.planbmsg.msg4000

import com.deepblue.library.planbmsg.Request
import com.deepblue.library.planbmsg.bean.CommonTask
import com.deepblue.library.planbmsg.bean.DeliverTask

class NewTaskReq : Request {

    constructor(task: CommonTask) : super(4000, "新建任务") {
        json = task
    }

    constructor(task: DeliverTask) : super(4001, "新建送货任务") {
        json = task
    }
}