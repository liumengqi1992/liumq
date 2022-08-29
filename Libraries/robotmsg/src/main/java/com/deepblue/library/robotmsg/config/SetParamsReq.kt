package com.deepblue.library.robotmsg.config

import com.deepblue.library.robotmsg.Request
import com.deepblue.library.robotmsg.bean.Settings

/**
 * 配置导航平台参数
 */
class SetParamsReq(settings: Settings) : Request(3008, "robot_config_setparams_req") {

    init {
        json = settings
    }
}
