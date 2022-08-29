package com.deepblue.library.crypto

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AES {

    private const val Algorithm = "AES"
    private const val AlgorithmPadding = "AES/CFB/NoPadding"

    fun encode(value: ByteArray, key: ByteArray, iv: ByteArray): ByteArray? {
        return try {
            val sr = IvParameterSpec(iv)
            val sk = SecretKeySpec(key, Algorithm)
            val cipher = Cipher.getInstance(AlgorithmPadding)
            cipher.init(Cipher.ENCRYPT_MODE, sk, sr)
            cipher.doFinal(value)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun decode(value: ByteArray, key: ByteArray, iv: ByteArray): ByteArray? {
        return try {
            val sr = IvParameterSpec(iv)
            val sk = SecretKeySpec(key, Algorithm)
            val cipher = Cipher.getInstance(AlgorithmPadding)
            cipher.init(Cipher.DECRYPT_MODE, sk, sr)
            cipher.doFinal(value)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}