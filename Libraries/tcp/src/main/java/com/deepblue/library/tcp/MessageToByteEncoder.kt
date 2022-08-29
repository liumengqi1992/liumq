package com.deepblue.library.tcp

abstract class MessageToByteEncoder {
    @Throws(Exception::class)
    abstract fun encode(message: TcpMessage, byteBuf: ByteBuf)
}