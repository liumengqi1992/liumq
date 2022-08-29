package com.deepblue.library.utils


object ByteUtils {

    fun getStringFromByteArray(data: ByteArray, cover: Char): String = byteArray2String(data, cover)

    fun byteArray2String(data: ByteArray, cover: Char): String {
        var index = 0
        for (i in 0 until data.size) {
            if (data[i] == cover.toByte()) {
                index = i
                break
            }
        }
        if (index == 0) {
            return String(data)
        }
        val array = ByteArray(index)
        System.arraycopy(data, 0, array, 0, index)
        return String(array)
    }
}