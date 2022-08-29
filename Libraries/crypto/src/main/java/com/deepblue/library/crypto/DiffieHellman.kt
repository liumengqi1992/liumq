package com.deepblue.library.crypto

import java.math.BigInteger
import java.security.*
import javax.crypto.spec.DHParameterSpec
import javax.crypto.KeyAgreement
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.interfaces.DHPublicKey
import kotlin.random.Random
import javax.crypto.spec.DHPublicKeySpec


class DiffieHellman {

    companion object {
        private val TAG = DiffieHellman::class.java.simpleName
    }

    private val ALGORITHM = "DH"

    private val P = BigInteger(
        "B10B8F96A080E01DDE92DE5EAE5D54EC52C99FBCFB06A3C6"
        +"9A6A9DCA52D23B616073E28675A23D189838EF1E2EE652C0"
        +"13ECB4AEA906112324975C3CD49B83BFACCBDD7D90C4BD70"
        +"98488E9C219A73724EFFD6FAE5644738FAA31A4FF55BCCC0"
        +"A151AF5F0DC8B4BD45BF37DF365C1A65E68CFDA76D4DA708"
        +"DF1FB2BC2E4A4371", 16)

    private val G = BigInteger(
        "A4D1CBD5C3FD34126765A442EFB99905F8104DD258AC507F"
        +"D6406CFF14266D31266FEA1E5C41564B777E690F5504F213"
        +"160217B4B01B886A5E91547F9E2749F4D7FBD7D3B9A92EE1"
        +"909D0D2263F80A76A6A24C087A091F531DBF0A0169B6A28A"
        +"D662A4D18E73AFA32D779D5918D08BC8858F4DCEF97C2A24"
        +"855E6EEB22B3B2E5", 16)

//    private val Q = BigInteger("F518AA8781A8DF278ABA4E7D64B7CB9D49462353", 16)

    private var secretKey: ByteArray? = null
    private val publicKey: PublicKey
//    private val publicKey: BigInteger
    private val kp: KeyPair
    private val ka: KeyAgreement
    private val iv = ByteArray(16)

    @Throws(Exception::class)
    constructor() {

        val keyGen = KeyPairGenerator.getInstance(ALGORITHM)

        val dhSpec = DHParameterSpec(P, G)

        keyGen.initialize(dhSpec)

        kp = keyGen.generateKeyPair()

        ka = KeyAgreement.getInstance(ALGORITHM)
        ka.init(kp.private)

        publicKey = kp.public
//        publicKey = (kp.public as DHPublicKey).y

        val dhPublicKey = ((kp.public as DHPublicKey).y).toByteArray()
        if (dhPublicKey.size != 129 && dhPublicKey.size != 128) {
            throw Exception("DH密钥非法")
        }

        val formatter = SimpleDateFormat("yyyyMMddHHmmssSS")
        val timestamp = formatter.format(Date())

        for (i in 0 until iv.size) {
            iv[i] = (timestamp[iv.size - i - 1].toInt() * Random(timestamp.toDouble().toInt()).nextInt()).toByte()
        }
    }

    @Synchronized
    fun setReceivedPublicKey(receivedPublicKey: ByteArray): Boolean {
        return try {
//            val keySpec = getDHPublicKeySpec(receivedPublicKey)
            val y = BigInteger(receivedPublicKey)
            val keySpec = DHPublicKeySpec(y, P, G)
            val keyFactory = KeyFactory.getInstance(ALGORITHM)
            val publicKey = keyFactory.generatePublic(keySpec)
            ka.doPhase(publicKey, true)
            val key = ka.generateSecret()
            secretKey = SHA256.getSHA256(key)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getSecretKey(): ByteArray? {
        return secretKey
    }

    fun getPublicKey(): ByteArray {
        val dhpk = publicKey as DHPublicKey
        val array = dhpk.y.toByteArray()
        val data = ByteArray(128)
        return if (array.size > data.size) {
            System.arraycopy(array, array.size - data.size, data, 0, data.size)
            data
        } else {
            array
        }
    }

    fun getIV(): ByteArray {
        return iv
    }
}