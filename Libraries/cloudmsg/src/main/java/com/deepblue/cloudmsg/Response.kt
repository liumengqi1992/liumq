package com.deepblue.cloudmsg

/**
 * εεΊζ₯ζ
 */
open class Response: Request() {

    companion object {
        const val SUCCESS_CODE = "1111"
    }

    var code = ""
    var msg = ""
    var data = Any()
}