package com.deepblue.library.websocket

import android.content.Context
import android.text.TextUtils
import com.deepblue.logd.Log
import okhttp3.*
import java.util.concurrent.TimeUnit

class WebSocketClient3(url: String) {
    private var mWebSocket: WebSocket? = null
    private var client: OkHttpClient? = null

    @JvmField
    var host: String = ""
    private var socketMessageCallback: SocketMessageCallback? = null

    var connectStatus = -1

    init {
        this.host = url
    }

    fun setHostUrl(url: String) {
        this.host = url
    }

    fun connect(url: String) {
//        FLog.i("robot", "connect:" + url)
        try {
            if (connectStatus != CONNECT_CONNECTING) {
                connectStatus = CONNECT_CONNECTING
                host = url

                if (socketMessageCallback != null) {
                    socketMessageCallback?.onSocketStatus(CONNECT_CONNECTING)
                }
                val request = Request.Builder().url(url).build()
                client = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()
                val webSocketListener = object : WebSocketListener() {
                    override fun onOpen(webSocket: WebSocket, response: Response) {
                        super.onOpen(webSocket, response)
//                        FLog.i("robot", "connect:open")
                        connectStatus = CONNECT_SUCCESS
                        if (socketMessageCallback != null) {
                            socketMessageCallback?.onSocketStatus(CONNECT_SUCCESS)
                        }
                    }

                    override fun onFailure(
                        webSocket: WebSocket,
                        t: Throwable,
                        response: Response?
                    ) {
                        super.onFailure(webSocket, t, response)
//                        FLog.i("robot", "connect:onFailure")
                        t.printStackTrace()
                        if (connectStatus != CONNECT_ERROR) {
                            connectStatus = CONNECT_ERROR
                            if (socketMessageCallback != null) {
                                socketMessageCallback?.onSocketStatus(CONNECT_ERROR)
                            }
                        }
                    }


                    override fun onMessage(webSocket: WebSocket, text: String) {
                        if (!TextUtils.isEmpty(text) && socketMessageCallback != null) {
                            socketMessageCallback?.onMessage(text)

                        }
                    }

                    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                        super.onClosed(webSocket, code, reason)
//                        FLog.i("robot", "connect:onClosed")
                        if (connectStatus != CONNECT_CLOSE && socketMessageCallback != null) {
                            connectStatus = CONNECT_CLOSE
                            socketMessageCallback?.onSocketStatus(CONNECT_CLOSE)
                        }
                    }

                    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                        super.onClosing(webSocket, code, reason)
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
        if (mWebSocket != null && connectStatus == CONNECT_SUCCESS) {
            mWebSocket?.send(message)
            Log.e("send",message)
        } else {
            if (connectStatus != CONNECT_CONNECTING) {
                connect(host)
            }
        }
    }

    fun initLog(context: Context) {
//        BaseLog.setFilePath(context)
    }


    fun isConnected(): Boolean {
        return connectStatus == CONNECT_SUCCESS
    }


    fun destroy() {
        mWebSocket?.close(1000, "null")
        mWebSocket = null
        webSocketClient?.connectStatus = -1
        webSocketClient = null
        client = null
        host = ""
    }

    fun setSocketCallBack(socketMessageCallback: SocketMessageCallback) {
        webSocketClient?.socketMessageCallback = socketMessageCallback
    }

    companion object {

        private var webSocketClient: WebSocketClient3? = null
        const val CONNECT_CONNECTING = 0//正在连接
        const val CONNECT_SUCCESS = 1//连接成功
        const val CONNECT_ERROR = 2//连接失败
        const val CONNECT_CLOSE = 3//连接断开

        @Synchronized
        @JvmStatic
        fun getInstance(url: String): WebSocketClient3 {
            if (webSocketClient == null) {
                webSocketClient = WebSocketClient3(url)
            }
            return webSocketClient!!
        }

    }
}
