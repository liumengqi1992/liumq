package com.deepblue.library.netty

interface NettyClientListener<T> {

    /**
     * 当接收到系统消息
     */
    fun onMessageResponse(msg: T)

    fun onMessageRequest(msg: T, isSuccess: Boolean)

    /**
     * 当服务状态发生变化时触发
     */
    fun onClientStatusConnectChanged(statusCode: Byte)

    fun onReconnect()

    companion object {

        const val STATUS_CONNECT_SUCCESS: Byte = 1

        const val STATUS_CONNECT_CLOSED: Byte = 2

        const val STATUS_CONNECT_RECONNECT: Byte = 3

        const val STATUS_CONNECT_EXCEPTION: Byte = 4
    }
}
