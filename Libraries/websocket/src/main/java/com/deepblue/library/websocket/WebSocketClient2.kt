package com.deepblue.library.websocket

import android.text.TextUtils
import com.deepblue.liblog.FLog
import okhttp3.*
import org.jetbrains.anko.doAsync
import java.util.*
import java.util.concurrent.TimeUnit

@Deprecated("WebSocketClient3")
class WebSocketClient2 {
    private var mWebSocket: WebSocket? = null
    private var client: OkHttpClient? = null
    @JvmField
    var host: String = ""
    private var socketMessageCallback: SocketMessageCallback? = null

    //发送报文计数器
    private var countSend = 0
    //收到报文计数器
    private var countReceive = 0

    var connectStatus = -1
    //发送消息队列
    private val messages = LinkedList<String>()


    fun connect(url: String) {

        try {
            if (connectStatus != CONNECT_CONNECTING) {
                connectStatus = CONNECT_CONNECTING
                host = url
                FLog.d("connect", "url: $url")
                val request = Request.Builder().url(url).build()
                client = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()
//                client = OkHttpClient()
                val webSocketListener = object : WebSocketListener() {
                    override fun onOpen(webSocket: WebSocket, response: Response) {
                        FLog.e("WebSocketClient", "onOpen")
                        super.onOpen(webSocket, response)
                        connectStatus = CONNECT_SUCCESS
                        countReceive = 0
                        countSend = 0
                        socketMessageCallback?.onSocketStatus(CONNECT_SUCCESS)

                        doAsync {
                            while (messages.isNotEmpty()) {
                                val message = messages.last
                                countSend++
                                webSocket.send(message)
                                Thread.sleep(100)
                                messages.removeLast()
                            }
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
                        if (connectStatus != CONNECT_ERROR) {
                            connectStatus = CONNECT_ERROR
                            socketMessageCallback?.onSocketStatus(CONNECT_ERROR)
                        }
                        mWebSocket?.close(1000, "null")
                        mWebSocket = null
                    }


                    override fun onMessage(webSocket: WebSocket, text: String) {
//                        KLog.e("onMessage", "onMessage: $text" )
                        if (!TextUtils.isEmpty(text)) {
                            countReceive++
                            socketMessageCallback?.onMessage(text)
                        }
                    }

                    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                        super.onClosed(webSocket, code, reason)
                        FLog.e("WebSocketClient", "onClosed: $code == $reason")
                        if (connectStatus != CONNECT_CLOSE) {
                            connectStatus = CONNECT_CLOSE
                            socketMessageCallback?.onSocketStatus(CONNECT_CLOSE)
                        }

                        mWebSocket?.close(1000, "null")
                        mWebSocket = null
                    }

                    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                        super.onClosing(webSocket, code, reason)
                        FLog.e("WebSocketClient", "onClosing: $code == $reason")
                        if (connectStatus != CONNECT_CLOSE) {
                            connectStatus = CONNECT_CLOSE
                            socketMessageCallback?.onSocketStatus(CONNECT_CLOSE)
                        }
                    }
                }
                client?.dispatcher?.cancelAll()
                mWebSocket = client?.newWebSocket(request, webSocketListener)
            }
        } catch (e: Exception) {
            connectStatus = -1
            e.printStackTrace()
        }
    }

    fun sendMessage(message: String) {

//        if (countSend - countReceive > 100) {
//            countSend = 0
//            countReceive = 0
//            socketMessageCallback?.onSocketStatus(CONNECT_ERROR)
//            return
//        }

        if (mWebSocket != null && connectStatus == CONNECT_SUCCESS) {
            countSend++
            mWebSocket?.send(message)
        } else {
            if (message.contains("获取用户列表")) {
                messages.addLast(message)
            }

            if (connectStatus != CONNECT_CONNECTING) {
                connect(host)
            }
        }
    }


    fun destroy() {
        mWebSocket?.close(1000, "null")
        mWebSocket = null
//        client?.dispatcher()?.executorService()?.shutdown()
//        webSocketClient?.socketMessageCallback = null
        webSocketClient?.connectStatus = -1
        webSocketClient = null
        client = null
        host = ""
    }

    companion object {

        private var webSocketClient: WebSocketClient2? = null


        const val CONNECT_CONNECTING = 0//正在连接
        const val CONNECT_SUCCESS = 1//连接成功
        const val CONNECT_ERROR = -1//连接失败
        const val CONNECT_CLOSE = 3//连接断开

        @Synchronized
        @JvmStatic
        fun getInstance(socketMessageCallback: SocketMessageCallback? = null): WebSocketClient2 {
            if (webSocketClient == null) {
                webSocketClient = WebSocketClient2()
            }
            webSocketClient?.socketMessageCallback = socketMessageCallback

            return webSocketClient!!
        }

    }
}
