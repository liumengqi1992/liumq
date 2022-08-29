package com.deepblue.library.tcp

abstract class TcpMessage {

    //需要断开并重连
    @JvmField
    var needReconnect = false

    /**
     * 报文长度
     */
    abstract fun length(): Int
}