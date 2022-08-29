package com.deepblue.library.tcp

import java.net.Socket
import java.net.InetSocketAddress
import java.io.IOException
import java.io.DataOutputStream
import java.io.EOFException
import java.net.SocketException
import java.io.DataInputStream
import java.net.ConnectException


class TcpClient(private val host: String, private val port: Int) {

    companion object {
        const val Timeout = 5000
    }
    private var messageToByteEncoder: MessageToByteEncoder? = null
    private var byteToMessageDecoder: ByteToMessageDecoder? = null
    private var listener: TcpListener? = null
    private var timeHeartBeat = 5000L
    private var msgHeartBeat: TcpMessage? = null


    private var dos: DataOutputStream? = null
    private var dis: DataInputStream? = null
    private var socket: Socket? = null
    //正在连接
    private var isConnecting = false
    //已连接
    @JvmField
    var isConnected = false
    //重连次数
    private var ReconnectNum = Integer.MAX_VALUE
    private var reconnectCount = 0
    private var heartBeatThread: HeartBeatThread? = null

    private var isNeedReconnect = true

    @JvmField
    var lockSender = Any()
    var lockReceiver = Any()

    /**
     * 读流线程
     */
    private var readDataThread: ReadDataThread? = null

    private inner class ReadDataThread : Thread() {
        override fun run() {
            while (isConnected) {
                try {
                    synchronized(lockReceiver) {
                        dis = DataInputStream(socket!!.getInputStream())
                        val byteBuffer = ByteBuf(dis!!)
                        val msg = byteToMessageDecoder!!.decode(byteBuffer)
                        listener?.onMessageResponse(msg)
                    }
                } catch (e: ConnectException) {
                    e.printStackTrace()
                    listener?.onClientStatusConnectChanged(TcpListener.STATUS_CONNECT_CLOSED)
                    closed()
                } catch (e: SocketException) {
                    e.printStackTrace()
                    listener?.onClientStatusConnectChanged(TcpListener.STATUS_CONNECT_CLOSED)
                    closed()
                } catch (e: EOFException) {

                } catch (e: Exception) {
                    e.printStackTrace()
                    listener?.onClientStatusConnectChanged(TcpListener.STATUS_CONNECT_EXCEPTION)
                    closed()
                }

            }
        }
    }

    private fun startReadData() {
        if (readDataThread == null) {
            readDataThread = ReadDataThread()
            readDataThread?.start()
        }
    }

    private inner class HeartBeatThread : Thread() {

        override fun run() {
            while (isConnected) {
                try {
                    Thread.sleep(timeHeartBeat)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                if (isConnected) {
                    sendMessage(msgHeartBeat!!)
                }
            }
        }
    }

    fun setMessageToByteEncoder(messageToByteEncoder: MessageToByteEncoder) {
        this.messageToByteEncoder = messageToByteEncoder
    }

    fun setByteToMessageDecoder(byteToMessageDecoder: ByteToMessageDecoder) {
        this.byteToMessageDecoder = byteToMessageDecoder
    }

    fun setHeartBeat(msg: TcpMessage) {
        setHeartBeat(msg, 5000L)
    }

    fun setHeartBeat(msg: TcpMessage, time: Long) {
        msgHeartBeat = msg
        timeHeartBeat = time
    }

    fun setListener(listener: TcpListener) {
        this.listener = listener
    }

    fun setReconnectNum(reconnectNum: Int) {
        this.ReconnectNum = reconnectNum
    }

    fun disconnect(isNeedReconnect: Boolean) {
        this.isNeedReconnect = isNeedReconnect
        closed()
    }

    private fun reconnect() {
        reconnectCount++
        if (ReconnectNum != Integer.MAX_VALUE && reconnectCount > ReconnectNum) {
            return
        }
        try {
            Thread.sleep(Timeout.toLong())
        } catch (e: Exception) {
        }

        listener?.onClientStatusConnectChanged(TcpListener.STATUS_CONNECT_RECONNECT)
        connect(false)
    }

    @Synchronized
    fun connect() {
        connect(true)
    }

    @Synchronized
    private fun connect(isUserOperate: Boolean) {
        if (isUserOperate) {
            reconnectCount = 0
        }
        object : Thread() {

            override fun run() {
                if (isConnecting) {
                    return
                }
                try {
                    isConnecting = true
                    isConnected = false

                    socket = Socket()
                    socket!!.connect(InetSocketAddress(host, port), Timeout)
                    dos = DataOutputStream(socket!!.getOutputStream())

                    listener?.onClientStatusConnectChanged(TcpListener.STATUS_CONNECT_SUCCESS)

                    heartBeatThread = HeartBeatThread()
                    heartBeatThread!!.start()

                    isConnecting = false
                    isConnected = true
                } catch (e: Exception) {
                    e.printStackTrace()
                    closed()
                }

            }
        }.start()
    }

    private fun sendMessage(msg: TcpMessage) {
        if (!isConnected) {
            closed()
            return
        }

        try {
            synchronized(lockSender) {
                val byteBuffer = ByteBuf(msg.length())
                if (messageToByteEncoder != null) {
                    messageToByteEncoder!!.encode(msg, byteBuffer)
                }
                val bytes = byteBuffer.getBytes()
                if (bytes != null) {
                    if (dos != null) {
                        dos!!.write(bytes)
                        dos!!.flush()
                    }
                    if (listener != null) {
                        listener!!.onMessageRequest(msg, true)
                    }
                }
            }
            startReadData()
        } catch (e: Exception) {
            e.printStackTrace()
            if (listener != null) {
                listener!!.onMessageRequest(msg, false)
                listener!!.onClientStatusConnectChanged(TcpListener.STATUS_CONNECT_EXCEPTION)
            }
            closed()
        }

    }

    @Synchronized
    private fun closed() {
        isConnecting = false
        isConnected = false
        heartBeatThread = null
        readDataThread = null
        if (dos != null) {
            try {
                dos!!.close()
                dos = null
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        if (dis != null) {
            try {
                dis!!.close()
                dis = null
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        if (socket != null) {
            try {
                socket!!.close()
                socket = null
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        if (isNeedReconnect) {
            reconnect()
        } else {
            listener = null
        }
    }

    fun sendMsgToServerWithThread(msg: TcpMessage/*, final boolean needRead*/) {
        object : Thread() {

            override fun run() {
                sendMessage(msg/*, needRead*/)
            }
        }.start()
    }

    fun sendMsgToServerWithoutThread(msg: TcpMessage) {
        sendMessage(msg)
    }
}