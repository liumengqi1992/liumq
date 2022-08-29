package com.deepblue.library.robotmsg.bean

class InitInfo {

    var name: String = ""
    var init: Boolean = false
    var error_msg = ArrayList<ErrorCode>()
    var reason: String = ""
    var suggestion: String = ""
}