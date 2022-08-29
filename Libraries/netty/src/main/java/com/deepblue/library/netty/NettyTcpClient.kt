package com.deepblue.library.netty

import android.os.SystemClock
import io.netty.bootstrap.Bootstrap
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.ByteToMessageDecoder
import io.netty.handler.codec.MessageToByteEncoder
import io.netty.handler.timeout.IdleStateHandler
import java.util.concurrent.TimeUnit


/**
 * TCP 客户端
 */
class NettyTcpClient<T>(private val host: String, private val port: Int) {

    private var group: NioEventLoopGroup? = null

    private var listener: NettyClientListener<T>? = null

    private var channel: Channel? = null

    //所有协议统一返回处理
    private var byteToMessageDecoder: ByteToMessageDecoder? = null
    //所有协议统一输出处理
    private var messageToByteEncoder: MessageToByteEncoder<T>? = null

    //心跳处理
    private var heartbeatHandlerAdapter: ChannelInboundHandlerAdapter? = null

    fun setByteToMessageDecoder(byteToMessageDecoder: ByteToMessageDecoder?) {
        this.byteToMessageDecoder = byteToMessageDecoder
    }

    fun setMessageToByteEncoder(messageToByteEncoder: MessageToByteEncoder<T>?) {
        this.messageToByteEncoder = messageToByteEncoder
    }

    fun setHeartbeatHandlerAdapter(heartbeatHandlerAdapter: ChannelInboundHandlerAdapter?) {
        this.heartbeatHandlerAdapter = heartbeatHandlerAdapter
    }

    /**
     * 获取TCP连接状态
     * @return
     */
    var isConnected = false

    private var isNeedReconnect = true
    var isConnecting = false
        private set

    private var reconnectIntervalTime: Long = 5000

    fun connect() {
        if (isConnecting) {
            return
        }

        listener?.onReconnect()
        val clientThread = object : Thread("client-Netty") {
            override fun run() {
                super.run()
                isNeedReconnect = true
                reconnectCount = ReconnectNum
                connectServer()
            }
        }
        clientThread.start()
    }


    private fun connectServer() {
        synchronized(this@NettyTcpClient) {
            var channelFuture: ChannelFuture? = null
            if (!isConnected) {
                isConnecting = true
                group = NioEventLoopGroup()

                val handlerChannel = object : ChannelInitializer<SocketChannel>() {
                    @Throws(Exception::class)
                    public override fun initChannel(ch: SocketChannel) {
                        //                            ch.pipeline().addLast(LineBasedFrameDecoder(1024))//黏包处理

                        if (heartbeatHandlerAdapter != null) {
                            ch.pipeline().addLast(
                                "ping",
                                IdleStateHandler(0, 5, 0, TimeUnit.SECONDS)
                            )//5s未发送数据，回调userEventTriggered

                            ch.pipeline().addLast(heartbeatHandlerAdapter)
                        }

                        if (messageToByteEncoder != null) {
                            ch.pipeline().addLast("encoder", messageToByteEncoder)
                        }

                        if (byteToMessageDecoder != null) {
                            ch.pipeline().addLast("decoder", byteToMessageDecoder)
                        }

                        ch.pipeline().addLast(NettyClientHandler(listener))
                    }
                }

                val bootstrap = Bootstrap().group(group!!)
                    .option(ChannelOption.TCP_NODELAY, true)//屏蔽Nagle算法试图
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MILLIS)
                    .channel(NioSocketChannel::class.java)
                    .handler(handlerChannel)

                try {
                    val cfListener = ChannelFutureListener { channelFuture ->
                        if (channelFuture.isSuccess) {
//                            KLog.e(TAG, "连接成功")
                            isConnected = true
                            channel = channelFuture.channel()
                        } else {
//                            KLog.e(TAG, "连接失败")
                            isConnected = false
                        }
                        isConnecting = false
                    }
                    channelFuture = bootstrap.connect(host, port).addListener(cfListener).sync()

                    // Wait until the connection is closed.
                    channelFuture!!.channel().closeFuture().sync()
//                    KLog.e(TAG, " 断开连接")
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    isConnected = false
                    listener!!.onClientStatusConnectChanged(NettyClientListener.STATUS_CONNECT_EXCEPTION)
                    if (null != channelFuture) {
                        if (channelFuture.channel() != null && channelFuture.channel().isOpen) {
                            channelFuture.channel().close()
                        }
                    }
                    group!!.shutdownGracefully()
                    reconnect()
                }
            }
        }
    }


    fun disconnect() {
//        KLog.e(TAG, "disconnect")
        isNeedReconnect = false
        group!!.shutdownGracefully()
    }

    fun reconnect() {
//        KLog.e(TAG, "reconnect")
        if (isNeedReconnect && reconnectCount > 0 && !isConnected) {
            reconnectCount--
            SystemClock.sleep(reconnectIntervalTime)
            if (isNeedReconnect && reconnectCount > 0 && !isConnected) {
//                KLog.e(TAG, "重新连接")
                listener?.onClientStatusConnectChanged(NettyClientListener.STATUS_CONNECT_RECONNECT)
                listener?.onReconnect()
                connectServer()
            }
        }
    }

    /**
     * 发送
     * @param data
     * @return
     */
    fun sendMsgToServer(data: T): Boolean {
        val flag = channel != null && isConnected
        if (flag) {
            val isSuccess = channel!!.writeAndFlush(data).awaitUninterruptibly().isSuccess
            listener!!.onMessageRequest(data, isSuccess)
        }
        return flag
    }

    /**
     * 设置重连次数
     * @param reconnectNum
     */
    fun setReconnectNum(reconnectNum: Int) {
        NettyTcpClient.ReconnectNum = reconnectNum
    }

    /**
     * 设置重连间隔
     * @param reconnectIntervalTime
     */
    fun setReconnectIntervalTime(reconnectIntervalTime: Long) {
        this.reconnectIntervalTime = reconnectIntervalTime
    }

    fun setListener(listener: NettyClientListener<T>) {
        this.listener = listener
    }

    companion object {
        private val TAG = NettyTcpClient::class.java.simpleName

        private var ReconnectNum = 5
        private var reconnectCount = ReconnectNum
        private val CONNECT_TIMEOUT_MILLIS = 5000
    }

}
