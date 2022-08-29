package com.deepblue.library.planbmsg.bean

/**
 * 初始化信息
 * 14000
 */
class InitInfo {

    //设备名字
    var name = ""

    //初始化成功
    var init = false
    var status = false

    //初始化失败的原因
    var reason = ""

    //初始化失败的建议
    var suggestion = ""
    var error_code = 0
}