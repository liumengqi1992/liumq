package com.deepblue.libchefcar

import com.socks.library.KLog
import org.jetbrains.anko.doAsync
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.nio.charset.Charset

class UDPManager(listener: Listener, timeout: Long, private val isServer: Boolean = false) {
    private val tag = "UDPManager"

    interface Listener {
        fun onReceive(carBean: CarBean)
        fun onIpBack(ip: String)
    }

    private var receiveSocket: DatagramSocket
    private var sendSocket: DatagramSocket? = null
    private val clientPort = 30001 //客户端端口
    private val serverPort = 30000 //服务端端口
    private var isRunning = true
//    private var ipServer = "255.255.255.255"

    init {
        if (sendSocket != null) {
            sendSocket!!.close()
            sendSocket = null
        }

        if (isServer) {
            sendSocket = DatagramSocket(clientPort)
            receiveSocket = DatagramSocket(serverPort)
            receiveSocket.soTimeout = timeout.toInt() * 4
        } else {
            sendSocket = DatagramSocket(serverPort)
            receiveSocket = DatagramSocket(clientPort)
            receiveSocket.soTimeout = timeout.toInt() * 2
        }
        sendSocket!!.broadcast = true

        doAsync {
            while (isRunning) {
                Thread.sleep(100)

                try {
                    val byte = ByteArray(1024)
                    val datagramPacket = DatagramPacket(byte, byte.size)
                    receiveSocket.receive(datagramPacket)
                    val data = String(
                            datagramPacket.data,
                            0,
                            datagramPacket.length,
                            Charset.forName("utf-8")
                    )
                    val carBean = JsonUtils.fromJson(data, CarBean::class.java) ?: continue
//                    if (isServer) {
                    carBean.ip = datagramPacket.address.hostAddress
//                    }
                    listener.onReceive(carBean)

                    listener.onIpBack(carBean.ip)
//                    if (!isServer) {
//                        ipServer = datagramPacket.address.hostAddress
//                    }
                } catch (e: Exception) {
                    KLog.e(tag, "Exception = " + e.message)
                    continue
                }
            }
            sendSocket!!.close()
            receiveSocket.close()
        }
    }

    fun destroy() {
        isRunning = false
    }

    fun sendMessage(carBean: CarBean) {
        doAsync {
            doSendMessage(carBean)
        }

    }

    private fun doSendMessage(carBean: CarBean) {
        try {
            val message = JsonUtils.toJSONString(carBean)
            val byte = message.toByteArray()
            val datagramPacket = DatagramPacket(byte, byte.size)
            datagramPacket.address = InetAddress.getByName("255.255.255.255")
            datagramPacket.port = if (isServer) clientPort else serverPort
            sendSocket!!.send(datagramPacket)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}