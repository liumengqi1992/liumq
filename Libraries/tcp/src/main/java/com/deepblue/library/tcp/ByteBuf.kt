package com.deepblue.library.tcp

import java.io.DataInputStream
import java.lang.NullPointerException
import java.nio.ByteBuffer

class ByteBuf {

    companion object {
        /**
         * byte：    1个字节 8位 -128~127
         * char：    2个字节 16位
         * short：   2个字节 16位
         * int：     4个字节 32位
         * float：   4个字节 32位
         * long：    8个字节 64位
         * double：  8个字节 64位
         */
        const val BYTE = 1
        const val CHAR = 2
        const val SHORT = 2
        const val INT = 4
        const val FLOAT = 4
        const val LONG = 8
        const val DOUBLE = 8

        private val TAG = ByteBuf::class.java.simpleName
    }

    private var byteBuffer: ByteBuffer? = null
    private var bbIndex = 0//读取byteBuffer索引序号
    private var dis: DataInputStream? = null

    constructor(capacity: Int) {
        allocate(capacity)
    }

    private fun allocate(capacity: Int) {
        byteBuffer = ByteBuffer.allocate(capacity)
    }

    /**
     * 封装DataInputStream，从中读取数据
     */
    constructor(dis: DataInputStream) {
        this.dis = dis
    }

    constructor(data: ByteArray, capacity: Int) {
        byteBuffer = ByteBuffer.allocate(capacity)
        byteBuffer?.put(data, 0, capacity)
    }

    fun writeByte(byte: Byte) {
        byteBuffer?.put(byte)
    }

    fun writeBoolean(boolean: Boolean) {
        val byte: Byte = if (boolean) 1 else 0
        byteBuffer?.put(byte)
    }

    fun writeBytes(bytes: ByteArray) {
        byteBuffer?.put(bytes)
    }

    fun getBytes(): ByteArray? {
        return byteBuffer?.array()
    }

    @Throws(Exception::class)
    fun readInt(): Int {
        if (dis == null) {
            if (byteBuffer == null) {
                throw NullPointerException()
            }
            flip()
            var int = byteBuffer!!.getInt(bbIndex)
            int = Integer.reverseBytes(int)
            bbIndex += INT
            return int
        }
        return Integer.reverseBytes(dis!!.readInt())
    }

    @Throws(Exception::class)
    fun readShort(): Short {
        if (dis == null) {
            if (byteBuffer == null) {
                throw NullPointerException()
            }
            flip()
            var short = byteBuffer!!.getShort(bbIndex)
            short = java.lang.Short.reverseBytes(short)
            bbIndex += SHORT
            return short
        }
        return java.lang.Short.reverseBytes(dis!!.readShort())
    }

    @Throws(Exception::class)
    fun readByte(): Byte {
        if (dis == null) {
            if (byteBuffer == null) {
                throw NullPointerException()
            }
            flip()
            val byte = byteBuffer!!.get(bbIndex)
            bbIndex += BYTE
            return byte
        }
        return dis!!.readByte()
    }

    @Throws(Exception::class)
    fun readBytes(bytes: ByteArray): Int {
        if (dis == null) {
            if (byteBuffer == null) {
                throw NullPointerException()
            }
            flip()
            byteBuffer!!.get(bytes)
            bbIndex += bytes.size
            return bytes.size
        }
        return dis!!.read(bytes)
    }

    private fun flip() {
        if (bbIndex == 0) {
            byteBuffer?.flip()
        }
    }

    fun writeShort(value: Short) {
//        val v = value.toInt()
//        val v1 = v.ushr(8) and 0xFF
//        val v0 = v.ushr(0) and 0xFF
//        byteBuffer?.put(v0.toByte())
//        byteBuffer?.put(v1.toByte())
        byteBuffer?.putShort(java.lang.Short.reverseBytes(value))
    }

    fun writeInt(value: Int) {
//        val v3 = value.ushr(24) and 0xFF
//        val v2 = value.ushr(16) and 0xFF
//        val v1 = value.ushr(8) and 0xFF
//        val v0 = value.ushr(0) and 0xFF
//        byteBuffer?.put(v0.toByte())
//        byteBuffer?.put(v1.toByte())
//        byteBuffer?.put(v2.toByte())
//        byteBuffer?.put(v3.toByte())
        byteBuffer?.putInt(Integer.reverseBytes(value))
    }

    fun writeLong(value: Long) {
//        val v7 = value.ushr(52) and 0xFF
//        val v6 = value.ushr(48) and 0xFF
//        val v5 = value.ushr(40) and 0xFF
//        val v4 = value.ushr(32) and 0xFF
//        val v3 = value.ushr(24) and 0xFF
//        val v2 = value.ushr(16) and 0xFF
//        val v1 = value.ushr(8) and 0xFF
//        val v0 = value.ushr(0) and 0xFF
//        byteBuffer?.put(v0.toByte())
//        byteBuffer?.put(v1.toByte())
//        byteBuffer?.put(v2.toByte())
//        byteBuffer?.put(v3.toByte())
//        byteBuffer?.put(v4.toByte())
//        byteBuffer?.put(v5.toByte())
//        byteBuffer?.put(v6.toByte())
//        byteBuffer?.put(v7.toByte())
        byteBuffer?.putLong(java.lang.Long.reverseBytes(value))
    }
}