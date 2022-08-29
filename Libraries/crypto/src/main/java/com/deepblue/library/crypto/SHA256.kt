package com.deepblue.library.crypto

import java.security.MessageDigest


object SHA256 {

    fun getSHA256(data: ByteArray): ByteArray? {
        try {
            val messageDigest = MessageDigest.getInstance("SHA-256")
            messageDigest.update(data)
            return messageDigest.digest()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}