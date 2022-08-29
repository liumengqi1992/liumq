package com.deepblue.libchefcar

import com.socks.library.KLog
import org.jetbrains.anko.doAsync

class ServerManager(private val listener: Listener) {

    private val tag = "ServerManager"

    companion object {
        private const val TIMEOUT_HEARTBEAT = 5000L
        private const val MAX_CLIENTS = 3
        private var udpServer: UDPManager? = null
        private var socketManagerServer: SocketManagerServer? = null
    }

    interface Listener {
        fun onOffline(carBean: CarBean): Boolean
        fun onLogin(carBean: CarBean, alert: Boolean): Boolean
        fun onCurrentPosition(carBean: CarBean): Boolean
        fun onDishDetect(carBean: CarBean): Boolean
        fun onStartHome(carBean: CarBean): Boolean
        fun onStartDeliver(carBean: CarBean): Boolean
        fun onReceive(carBean: CarBean): Boolean
    }

    val clients = ArrayList<CarBean>()
    //最近一次服务端发送的指令，确保客户端收到，若未收到，重发
//    private val lastClients = SparseArray<CarBean>()
    private var isRunning = true

    init {
        if (udpServer == null) {
            udpServer = UDPManager(object : UDPManager.Listener {

                override fun onIpBack(ip: String) {
                    KLog.i(tag, "[onIpBack] ip = $ip")
                }

                override fun onReceive(cb: CarBean) {
                    KLog.w(tag, "[onReceive] carBean.toString() = " + cb.toString())
                    val carBean = CarBean()
                    carBean.copy(cb)

//                    val currentCar = findCar(carBean.carCode)
//                    if (currentCar != null && carBean.countServer < currentCar.countServer) {
//                        //过时的消息，不处理
//                        KLog.w("udp.onReceive", "过时的消息")
//                        if (lastClients[currentCar.carCode] != null && lastClients[currentCar.carCode].countServer >= currentCar.countServer) {
//                            KLog.w("udp.onReceive", "重发上一条指令")
//                            sendMessage(lastClients[currentCar.carCode])
//                        }
//                        return
//                    }
                    carBean.timeServer = System.currentTimeMillis()
                    var isNewClient = false
                    if (carBean.carCode == CarBean.IDLE) {
                        if (clients.size < MAX_CLIENTS && carBean.currentPosition > CarBean.HOME) {
                            //未达到客户端数量上限，分配餐车编号
                            carBean.carCode = carBean.currentPosition
                            isNewClient = true
                        } else {
                            return
                        }
                    } else if (carBean.carCode > CarBean.IDLE && clients.size < MAX_CLIENTS) {

                        var found = false
                        for (i in 0 until clients.size) {
                            if (clients[i].carCode == carBean.carCode) {
                                found = true
                                listener.onLogin(carBean, false)
                            }
                        }
                        if (!found) {
                            isNewClient = true
                        }
                    }
                    KLog.d(tag, "isNewClient = " + isNewClient)
                    if (isNewClient) {
//                        KLog.w("udp.onReceive", carBean.toString())
                        if (clients.isEmpty()) {
                            clients.add(carBean)
                        } else {
                            var isAdded = false
                            for (i in 0 until clients.size) {
                                if (carBean.currentPosition > clients[i].carCode) {
                                    //餐车分配carCode时，必须放在厨师区的三个位置，编号最大的在列表中最靠前，以此区分一、二、三号车
                                    clients.add(i, carBean)
                                    isAdded = true
                                    break
                                }
                            }
                            if (!isAdded) {
                                clients.add(carBean)
                            }
                        }
                        listener.onLogin(carBean, true)
                    } else {
                        //改为Scoket
                        update(carBean)

                        //TODO 以下代码改为Socket长连接
//                        if (!carBean.isHeartBeat) {
////                            KLog.w("udp.onReceive", carBean.toString())
//                        } else if (lastClients[carBean.carCode] != null && lastClients[carBean.carCode].targetPosition != carBean.targetPosition) {
//                            //客户端未收到上一条指令，重发
//                            sendMessage(lastClients[carBean.carCode])
//                            return
//                        }
//                        for (i in 0 until clients.size) {
//                            if (clients[i].carCode == carBean.carCode) {
//
//                                var isListened = false
//
//                                clients[i].timeServer = carBean.timeServer
//                                if (clients[i].currentPosition != carBean.currentPosition) {
//                                    clients[i].copy(carBean)
//                                    listener.onCurrentPosition(carBean)
//                                    isListened = true
//                                }
//                                if (clients[i].dishDetect != carBean.dishDetect) {
//                                    clients[i].copy(carBean)
//                                    listener.onDishDetect(carBean)
//                                    isListened = true
//                                }
//                                if (!clients[i].isHoming && carBean.isHoming) {
//                                    clients[i].copy(carBean)
//                                    listener.onStartHome(carBean)
//                                    isListened = true
//                                }
//
//                                if (!isListened) {
//                                    clients[i].copy(carBean)
//                                    listener.onReceive(carBean)
//                                }
//                                break
//                            }
//                        }
                    }

                    carBean.isHeartBeat = true
                    carBean.timeServer = System.currentTimeMillis()
                    sendMessage(carBean)
                    return
                }
            }, TIMEOUT_HEARTBEAT, true)
        }

        if (socketManagerServer == null) {
            socketManagerServer = SocketManagerServer(object : SocketManagerServer.Listener {
                override fun onCurrentPosition(carBean: CarBean): Boolean {
                    KLog.w("socketManagerServer", "onCurrentPosition: $carBean")
                    update(carBean)
                    return listener.onCurrentPosition(carBean)
                }

                override fun onDishDetect(carBean: CarBean): Boolean {
                    KLog.w("socketManagerServer", "onDishDetect: $carBean")
                    update(carBean)
                    return listener.onDishDetect(carBean)
                }

                override fun onStartHome(carBean: CarBean): Boolean {
                    KLog.w("socketManagerServer", "onStartHome: $carBean")
                    update(carBean)
                    return listener.onStartHome(carBean)
                }

                override fun onStartDeliver(carBean: CarBean): Boolean {
                    KLog.w("socketManagerServer", "onStartDeliver: $carBean")
                    update(carBean)
                    return listener.onStartDeliver(carBean)
                }

                override fun onReceive(carBean: CarBean): Boolean {
                    KLog.w("socketManagerServer", "onReceive: $carBean")
                    update(carBean)
                    return listener.onReceive(carBean)
                }

                override fun onOffline(carBean: CarBean): Boolean {
                    KLog.w("socketManagerServer", "onOffline: $carBean")
                    update(carBean)
                    return listener.onOffline(carBean)
                }
            })
        }

        doAsync {
            while (isRunning) {
                Thread.sleep(TIMEOUT_HEARTBEAT)
                //检查客户端心跳是否还在，不在则从List中去除
                if (clients.isEmpty()) {
                    continue
                }
                for (i in clients.size - 1 downTo 0) {
                    if (clients[i].timeServer > 0 && System.currentTimeMillis() - clients[i].timeServer > TIMEOUT_HEARTBEAT * 6) {
                        KLog.e("onOffline", "carCode: ${clients[i].carCode}")
                        listener.onOffline(clients[i])
                        socketManagerServer?.remove(clients[i])
                        clients.removeAt(i)
                    }
                }
            }
            clients.clear()
            udpServer?.destroy()
            socketManagerServer?.destroy()
        }
    }

    private fun update(cb: CarBean) {
        val carBean = CarBean()
        carBean.copy(cb)
        carBean.timeServer = System.currentTimeMillis()
        for (i in 0 until clients.size) {
            if (clients[i].carCode == carBean.carCode) {
                clients[i].copy(carBean)
                break
            }
        }
    }

    fun findIndex(carBean: CarBean): Int {
        for (i in clients.indices) {
            if (clients[i].carCode == carBean.carCode) {
                return i
            }
        }
        return -1
    }

    fun findCar(carCode: Int): CarBean? {
        KLog.d("ServerManager", "[findCar]" + clients.indices)
        for (i in clients.indices) {
            if (clients[i].carCode == carCode) {
                return clients[i]
            }
        }
        return null
    }

    fun startDeliver(carBean: CarBean, targetPosition: Int, MOST_READY_CAR: Int) {
        //1到3号位置为餐车初始位置，因此从1开始编号的用户座位从4开始
        goPosition(carBean, targetPosition + MOST_READY_CAR)
        listener.onStartDeliver(carBean)
    }

    /**
     * 前往目标点
     * @param carBean
     * @param targetPosition 默认值是召回
     */
    fun goPosition(carBean: CarBean, targetPosition: Int = carBean.carCode) {
        //餐车初始位置，即作为餐车的编号，也是家的位置
//        carBean.targetPosition = targetPosition
//        carBean.isHeartBeat = false
        val cb = CarBean()
        cb.copy(carBean)
        cb.targetPosition = targetPosition
        cb.isHeartBeat = false
        sendMessage(cb)
    }

    fun setSpeed(carBean: CarBean, speed: Int) {
        val cb = CarBean()
        cb.copy(carBean)
        cb.speed = speed
        cb.isHeartBeat = false
        sendMessage(cb)
    }

    fun setSuspend(carBean: CarBean, suspend: Boolean) {
//        carBean.isSuspend = suspend
//        carBean.isHeartBeat = false
        val cb = CarBean()
        cb.copy(carBean)
        cb.isSuspend = suspend
        cb.isHeartBeat = false
        sendMessage(cb)
    }

    /**
     * 全体召回
     */
    fun goHomeAll() {
        //carCode
        //-1：发给所有餐车
        //0：所有餐车重置自己的carCode为0，等待厨师端重新分配
        //>0：发给指定餐车
        val carBean = CarBean()
        carBean.carCode = CarBean.ALL
        //0表示回家，餐车初始位置，即作为餐车的编号，也是家的位置
        carBean.targetPosition = CarBean.HOME
        carBean.isHeartBeat = false
        sendMessage(carBean)
    }

    private fun sendMessage(cb: CarBean) {
        synchronized(this) {

            val carBean = CarBean()
            carBean.copy(cb)
            carBean.timeServer = System.currentTimeMillis()
            if (!carBean.isHeartBeat) {
                carBean.countServer++
                socketManagerServer?.sendMessage(carBean)
                return
            }

//            if (!carBean.isHeartBeat) {
//                carBean.countServer++
//            }
            udpServer?.sendMessage(carBean)
            KLog.w(tag, "[sendMessage] carBean.toString() = " + carBean.toString())
            for (i in 0 until clients.size) {
                if (clients[i].carCode == carBean.carCode) {
                    clients[i].copy(carBean)
//                    if (!carBean.isHeartBeat) {
//                        lastClients.put(carBean.carCode, carBean)
//                    }
                    break
                } else if (carBean.carCode == CarBean.ALL && carBean.targetPosition == CarBean.HOME) {
                    val carCode = clients[i].carCode
                    val speed = clients[i].speed
                    clients[i].copy(carBean)
                    clients[i].carCode = carCode
                    clients[i].targetPosition = carCode
                    clients[i].speed = speed
//                    lastClients.put(clients[i].carCode, clients[i])
                }
            }
        }
    }

    fun destroy() {
        isRunning = false
        socketManagerServer?.destroy()
    }
}