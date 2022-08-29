package com.deepblue.library.planbmsg.bean

import android.util.Base64

class ConfigParam {

    companion object {
        const val FLAGS = Base64.NO_WRAP
    }

    val filename_list = ArrayList<String>()
    var filename = ""
    var data = ""
}