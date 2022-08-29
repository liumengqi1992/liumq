package com.deepblue.library.planbmsg

/**
 * 响应报文
 */
open class Response: Request() {

    var error_code: Int = 0
}
