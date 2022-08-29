package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request
import com.deepblue.library.planbmsg.bean.MapEdit

class UploadMapReq(map: MapEdit, action: Int = 0) : Request(3010, "地图编辑") {

    companion object {
        const val ACTION_ADD = 1
        const val ACTION_MODIFY = 2
        const val ACTION_DELETE = 3
    }

    init {
        number = action
        json = map
    }
}
