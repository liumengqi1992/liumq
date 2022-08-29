package com.deepblue.library.robotmsg

/**
 * 响应报文
 */
open class Response: Request() {

    var error_code: Int = 0
}
