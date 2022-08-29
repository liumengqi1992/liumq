package com.deepblue.library.netty

import com.socks.library.KLog
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

class NettyClientHandler<T>(private val listener: NettyClientListener<T>?) :
    SimpleChannelInboundHandler<String>() {

    override fun channelActive(ctx: ChannelHandlerContext) {
        KLog.i(TAG, "channelActive")
        listener?.onClientStatusConnectChanged(NettyClientListener.STATUS_CONNECT_SUCCESS)
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        KLog.i(TAG, "channelInactive")
        listener?.onClientStatusConnectChanged(NettyClientListener.STATUS_CONNECT_CLOSED)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        KLog.i(TAG, "exceptionCaught")
        listener?.onClientStatusConnectChanged(NettyClientListener.STATUS_CONNECT_EXCEPTION)
        cause.printStackTrace()
        ctx.close()
    }

    override fun channelRead0(channelHandlerContext: ChannelHandlerContext, s: String) {
        KLog.i(TAG, "channelRead0: $s")
//        listener?.onMessageResponseClient(s, index)
    }

    companion object {

        private val TAG = NettyClientHandler::class.java.simpleName
    }
}
