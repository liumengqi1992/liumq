package com.deepblue.library.robotmsg.config

import com.deepblue.library.robotmsg.Request
import com.deepblue.library.robotmsg.bean.Map

/**
 * 上传地图到机器人
 */
class UploadMapReq(map: Map) : Request(3000, "robot_config_uploadmap_req") {

    init {
        json = map
    }
}
