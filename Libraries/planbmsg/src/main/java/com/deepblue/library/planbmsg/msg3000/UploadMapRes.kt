package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.UploadMap

class UploadMapRes : Response() {
    
    init {
        json = UploadMap()
    }

    fun getJson(): UploadMap? {
        return JsonUtils.fromJson(json.toString(), UploadMap::class.java)
    }

}