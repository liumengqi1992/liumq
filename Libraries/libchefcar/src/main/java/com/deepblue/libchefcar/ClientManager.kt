package com.deepblue.libchefcar

import android.util.Log
import com.socks.library.KLog
import org.jetbrains.anko.doAsync

class ClientManager(private val listener: Listener) {

    companion object {
        private const val TIMEOUT_HEARTBEAT = 5000L
    }

    var HOST: String = ""

    interface Listener {
        /**
         * 连上厨师端
         */
        fun onLogin(carBean: CarBean)
        /**
         * 重置，将餐车自己的carCode置0，等待服务端重新分配
         */
        fun onReset(carBean: CarBean)

        /**
         * 目的地有变化
         */
        fun onTargetPosition(carBean: CarBean)

        /**
         * 停止/运行有变化
         */
        fun onSuspend(carBean: CarBean)

        fun onReceive(carBean: CarBean)

        fun onIpBack(ip: String)
    }

    private val udpClient: UDPManager
    var isRunning = true
    private val carBeanServer = CarBean()

    init {
        udpClient = UDPManager(object : UDPManager.Listener {
            override fun onIpBack(ip: String) {
                HOST = ip
                listener.onIpBack(HOST)
            }

            override fun onReceive(carBean: CarBean) {

                //carCode
                //-1：发给所有餐车
                //0：所有餐车重置自己的carCode为0，等待厨师端重新分配
                //>0：发给指定餐车
                if (carBean.carCode == CarBean.IDLE) {
                    KLog.w("udp_onReceive", "@@@@@@@@@@@@@")
                    listener.onReset(carBean)
                    carBeanServer.copy(carBean)
                    return
                }
                if (carBeanServer.carCode == CarBean.IDLE && carBean.carCode == carBeanServer.currentPosition) {
                    KLog.w("udp_onReceive", "##############")
                    listener.onLogin(carBean)
                    carBeanServer.copy(carBean)
                    return
                }
                if (carBean.carCode == CarBean.ALL || carBean.carCode == carBeanServer.carCode) {
                    if (carBean.carCode == CarBean.ALL && carBean.targetPosition == CarBean.HOME) {
                        KLog.w("udp_onReceive", "*********")

                        //全体召回
                        val carCode = carBeanServer.carCode
                        carBeanServer.copy(carBean)
                        carBeanServer.targetPosition = carCode
                        carBeanServer.isHoming = true
                        listener.onTargetPosition(carBeanServer)
                    } else if (carBeanServer.targetPosition != carBean.targetPosition) {
                        KLog.w("udp_onReceive", "!!!!!!!!!")
                        carBeanServer.copy(carBean)
                        listener.onTargetPosition(carBean)
                    } else if (carBeanServer.isSuspend != carBean.isSuspend) {
                        KLog.w("udp_onReceive", "****^^^^^^^^^")
                        carBeanServer.copy(carBean)
                        listener.onSuspend(carBean)
                    } else {
                        carBeanServer.copy(carBean)
                        listener.onReceive(carBean)
                    }
                }
            }
        }, TIMEOUT_HEARTBEAT)

//        doAsync {
//            while (isRunning) {
//                if (System.currentTimeMillis() - oldTime > TIMEOUT_HEARTBEAT * 6 && oldTime > 0) {
//                    KLog.w(
//                            "udp_onReceive",
//                            System.currentTimeMillis().toString() + "****&&&&&" + oldTime
//                    )
//                    listener.onReset(carBeanServer)
//                }
//                sendMessage(carBeanServer, true)
//                Thread.sleep(TIMEOUT_HEARTBEAT)
//
//            }
//            udpClient.destroy()
//        }
    }

    fun sendMessage(carBean: CarBean, isHeartBeat: Boolean) {
        synchronized(this) {
            carBeanServer.copy(carBean, false)
            carBeanServer.isHeartBeat = isHeartBeat
            carBeanServer.countClient++;
            carBeanServer.timeClient = System.currentTimeMillis()
            udpClient.sendMessage(carBeanServer)
            Log.e("udp_send", carBeanServer.toString())
        }
    }


    fun destroy() {
        isRunning = false
        udpClient.destroy()
    }
}