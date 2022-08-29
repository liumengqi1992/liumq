package com.deepblue.library.tcp

abstract class ByteToMessageDecoder {
    @Throws(Exception::class)
    abstract fun decode(byteBuf: ByteBuf): TcpMessage?
}