package com.deepblue.libchefcar

import com.socks.library.KLog
import org.jetbrains.anko.doAsync
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
import java.io.DataInputStream
import java.io.DataOutputStream

class SocketManagerServer(private val listener: Listener) {

    interface Listener {
        fun onOffline(carBean: CarBean): Boolean
//        fun onLogin(carBean: CarBean)
        fun onCurrentPosition(carBean: CarBean): Boolean
        fun onDishDetect(carBean: CarBean): Boolean
        fun onStartHome(carBean: CarBean): Boolean
        fun onStartDeliver(carBean: CarBean): Boolean
        fun onReceive(carBean: CarBean): Boolean
    }

    private var serverSocket: ServerSocket
    private val serverPort = 29999 //服务端端口
    private var isRunning = true
    private val socketClients = ArrayList<ConnectThread>()

    init {

        serverSocket = ServerSocket(serverPort)

        doAsync {
            try {
                var socket: Socket? = null
                //等待连接，每建立一个连接，就新建一个线程
                while (isRunning) {
                    socket = serverSocket.accept()//等待一个客户端的连接，在连接之前，此方法是阻塞的
                    KLog.e("socket.accept", "有一个客户端的连接, socketClients.size: ${socketClients.size}")
                    val ct = ConnectThread(socket)
                    ct.start()
                    socketClients.add(ct)
                    Thread.sleep(50)
                }

                socket?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    internal inner class ConnectThread(private val socket: Socket) : Thread() {

        var carCode = 0
        private var isRunning = true
        private var dos:DataOutputStream? = null
        private val lastCarBean = CarBean()

        override fun run() {
            var carBean: CarBean? = null
            try {
                val dis = DataInputStream(socket.getInputStream())
                dos = DataOutputStream(socket.getOutputStream())
                while (isRunning) {
                    val data = dis.readUTF()
                    carBean = JsonUtils.fromJson(data, CarBean::class.java) ?: continue
                    KLog.e("socket.onReceive", carBean.toString())
                    carCode = carBean.carCode

                    var sentMessage = false
                    if (lastCarBean.carCode == 0) {
                        lastCarBean.copy(carBean)
                    } else {
                        var isListened = false

                        if (lastCarBean.currentPosition != carBean.currentPosition) {
                            if (listener.onCurrentPosition(carBean)) {
                                sentMessage = true
                            }
                            isListened = true
                        }
                        if (lastCarBean.dishDetect != carBean.dishDetect) {
                            if (listener.onDishDetect(carBean)) {
                                sentMessage = true
                            }
                            isListened = true
                        }
                        if (!lastCarBean.isHoming && carBean.isHoming) {
                            if (listener.onStartHome(carBean)) {
                                sentMessage = true
                            }
                            isListened = true
                        }

                        lastCarBean.copy(carBean)
                        if (!isListened) {
                            listener.onReceive(carBean)
                        }
                    }

                    if (!sentMessage) {
                        carBean.timeServer = System.currentTimeMillis()
                        sendMessage(carBean)
                        sleep(100)
                    }
                }

                dis.close()
                dos?.close()
            } catch (e: Exception) {
                e.printStackTrace()
//                isRunning = false
            }
            socketClients.remove(this)
            if (carBean != null) {
                listener.onOffline(carBean)
            }
        }

        fun doDestroy() {
            isRunning = false
        }

        fun sendMessage(carBean: CarBean) {
            val msg = JsonUtils.toJSONString(carBean)
            dos?.writeUTF(msg)
            dos?.flush()
            KLog.e("socket.sendMessage", msg)
            lastCarBean.copy(carBean)
        }
    }

    fun destroy() {
        isRunning = false
        for (i in socketClients.indices) {
            socketClients[i].doDestroy()
        }
    }

    fun remove(carBean: CarBean) {
        if (socketClients.isEmpty()) {
            return
        }
        for (i in socketClients.size - 1 downTo 0) {
            if (socketClients[i].carCode == carBean.carCode) {
                socketClients[i].doDestroy()
                socketClients.removeAt(i)
            }
        }
    }

    fun sendMessage(carBean: CarBean) {
        doAsync {
            var isSent = false
            for (i in socketClients.indices) {
                val sc = socketClients[i]
                if (sc.carCode == carBean.carCode) {
                    sc.sendMessage(carBean)
                    isSent = true
                } else if (carBean.carCode == CarBean.ALL && carBean.targetPosition == CarBean.HOME) {
                    val cb = CarBean()
                    cb.copy(carBean)
                    cb.carCode = socketClients[i].carCode
                    cb.targetPosition = cb.carCode
                    sc.sendMessage(cb)
                    isSent = true
                }
            }
            if (!isSent) {
                KLog.e("sendMessage", "carBean.carCode: ${carBean.carCode}, 指令未发出：${socketClients.size}")
            }
        }
    }
}