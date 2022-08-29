package com.deepblue.library.robotmsg.bean

import com.deepblue.library.robotmsg.control.GetPathRes

class ShowTask {
    var path = GetPathRes.Data()
    var name: String? = ""
    var isShow: Boolean = true
}