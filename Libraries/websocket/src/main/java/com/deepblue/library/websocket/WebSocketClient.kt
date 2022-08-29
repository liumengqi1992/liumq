package com.deepblue.library.websocket

import android.text.TextUtils
import com.deepblue.liblog.FLog
import okhttp3.*

@Deprecated("WebSocketClient2")
class WebSocketClient {
    private var mWebSocket: WebSocket? = null

    //    var isConnect: Boolean = false
//        private set
    private var isFirstFailure: Boolean = false

    //    private var keepLiveThread: Thread? = null
    private var client: OkHttpClient? = null

    //    private var request: Request? = null
    var host: String = ""

    //    private var keepTimes: Int = 0
    private var connectTimes: Int = 0
    private var socketMessageCallback: SocketMessageCallback? = null

    //发送报文计数器
    private var countSend = 0

    //收到报文计数器
    private var countReceive = 0

    var connectStatus = -1

//    /**
//     * 设置心跳Thread
//     * @param keepLiveThread
//     */
//    fun setKeepLiveThread(keepLiveThread: Thread) {
//        this.keepLiveThread = keepLiveThread
//    }

    fun connect(url: String, message: String? = null) {

        try {
            if (connectStatus != CONNECT_CONNECTING) {
                connectStatus = CONNECT_CONNECTING
                host = url
//        startThread()
                if (!TextUtils.isEmpty(message)) {
                }
                val request = Request.Builder().url(url).build()
                client = OkHttpClient()
                val webSocketListener = object : WebSocketListener() {
                    override fun onOpen(webSocket: WebSocket, response: Response) {
                        FLog.e("WebSocketClient", "onOpen")
                        super.onOpen(webSocket, response)
//                    isConnect = true
                        connectStatus = CONNECT_SUCCESS
                        //                keepTimes = 0
                        isFirstFailure = false
                        mWebSocket = webSocket
                        countReceive = 0
                        countSend = 0
                        socketMessageCallback?.onSocketStatus(CONNECT_SUCCESS)

                        if (message != null && message.isNotEmpty()) {
                            countSend++
                            webSocket.send(message)
                        }
                    }

                    override fun onFailure(
                        webSocket: WebSocket,
                        t: Throwable,
                        response: Response?
                    ) {
                        super.onFailure(webSocket, t, response)
                        FLog.e("WebSocketClient", "onFailure: $t")
                        t.printStackTrace()
                        if (!isFirstFailure) {
                            isFirstFailure = true
                            //                    keepTimes = 0
                        }
                        if (socketMessageCallback != null && connectTimes > 3) {
//                        isConnect = false
                            if (connectStatus != CONNECT_CLOSE) {
                                socketMessageCallback?.onSocketStatus(CONNECT_CLOSE)
                            }
                            connectStatus = CONNECT_CLOSE
                            //                    keepLiveThread = null
                            connectTimes = 0
                            isFirstFailure = false
//                        socketMessageCallback?.onSocketStatus(CONNECT_ERROR)
                        }
                        if (connectStatus != CONNECT_CLOSE) {
                            connectTimes++
                            connectStatus = CONNECT_ERROR
                            socketMessageCallback?.onSocketStatus(CONNECT_ERROR)
                        }
                    }

                    override fun onMessage(webSocket: WebSocket, text: String) {
                        countReceive++
                        socketMessageCallback?.onMessage(text)
                    }

                    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                        super.onClosed(webSocket, code, reason)
                        FLog.e("WebSocketClient", "onClosed: $code == $reason")
//                    isConnect = false
                        if (connectStatus != CONNECT_CLOSE) {
                            socketMessageCallback?.onSocketStatus(CONNECT_CLOSE)
                        }
                        connectStatus = CONNECT_CLOSE
                        //                keepLiveThread = null
                        mWebSocket?.close(1000, "null")
                        mWebSocket = null
                    }

                    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                        super.onClosing(webSocket, code, reason)
                        FLog.e("WebSocketClient", "onClosing: $code == $reason")
                        if (connectStatus != CONNECT_CLOSE) {
                            socketMessageCallback?.onSocketStatus(CONNECT_CLOSE)
                        }
                    }
                }
                client?.newWebSocket(request, webSocketListener)
            }
        } catch (e: Exception) {
            connectStatus = -1
            e.printStackTrace()
        }
    }

    fun sendMessage(message: String) {

        if (countSend - countReceive > 100) {
            countSend = 0
            countReceive = 0
            socketMessageCallback?.onSocketStatus(CONNECT_ERROR)
            return
        }

        if (mWebSocket != null && connectStatus == CONNECT_SUCCESS) {
            countSend++
            mWebSocket?.send(message)
//            client!!.dispatcher().executorService().shutdown()
        } else if (/*connectStatus != CONNECT_CLOSE && */connectStatus != CONNECT_CONNECTING) {
            connectTimes++
            connect(host, message)
        }
    }

    fun destroy() {
        mWebSocket?.close(1000, "null")
        mWebSocket = null
//        client?.dispatcher()?.executorService()?.shutdown()
        webSocketClient?.socketMessageCallback = null
        webSocketClient?.connectStatus = -1
        webSocketClient = null
        client = null
    }

    companion object {

        private var webSocketClient: WebSocketClient? = null


        const val CONNECT_CONNECTING = 0//正在连接
        const val CONNECT_SUCCESS = 1//连接成功
        const val CONNECT_ERROR = 2//连接失败
        const val CONNECT_CLOSE = 3//连接断开


        @Synchronized
        fun getInstance(socketMessageCallback: SocketMessageCallback): WebSocketClient {
            if (webSocketClient == null) {
                webSocketClient = WebSocketClient()
            }
            webSocketClient?.socketMessageCallback = socketMessageCallback
            return webSocketClient!!
        }

//        private var mKeepLiveThread: KeepLiveThread? = null
    }

//    private val keepLiveMessages = ArrayList<String>()

//    fun addKeepLiveMessage(message: String) {
//        keepLiveMessages.add(message)
//    }
//    private fun startThread() {
//
//        if (mKeepLiveThread == null) {
//            mKeepLiveThread = KeepLiveThread()
//            mKeepLiveThread?.start()
//        }
//    }
//    private inner class KeepLiveThread : Thread() {
//        override fun run() {
//            while (true) {
//                try {
//                    sleep(2000)
//                    keepTimes++
////                    sendMessage(JsonUtil.messageJson(999, ""))
//                    if (!isConnect) {
//                        connectTimes++
//                        connect(host)
//                    } else {
//                        connectTimes = 0
//                        if (keepTimes % 15 == 2) {
//
//                            for (i in 0 until keepLiveMessages.size) {
//                                sendMessage(keepLiveMessages[i])
//                                sleep(100)
//                            }
//                        }
//                    }
//
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//
//            }
//        }
//    }
}
