package com.deepblue.library.planbmsg

import com.alibaba.fastjson.JSON

/**
 * 请求报文
 */
open class Request {

    var type = 0
    var name = ""
    var json = Any()
    var number = 0

    constructor(): super()

    constructor(type: Int, name: String): this(type, name, Any())

    constructor(type: Int, name: String, number: Int): this(type, name, Any(), number)

    constructor(type: Int, name: String, json: Any): this(type, name, json, 0)

    constructor(type: Int, name: String, json: Any, number: Int) {
        this.type = type
        this.name = name
        this.json = json
        this.number = number
    }

    override fun toString(): String {
        return JSON.toJSONString(this)
    }
}