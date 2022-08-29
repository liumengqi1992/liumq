package com.deepblue.library.websocket

interface SocketMessageCallback {

    fun onMessage(message: String): Int

    fun onSocketStatus(status: Int)
}