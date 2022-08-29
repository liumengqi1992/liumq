package com.deepblue.libchefcar

import com.alibaba.fastjson.annotation.JSONField

class CarBean {

    companion object {
        const val ALL = -1
        const val IDLE = 0
        //0表示家，因此位置点从1开始编号
        const val HOME = 0
    }

    //本消息是否为心跳，由客户端设置
    var isHeartBeat = true
    //消息计数器（发送方+1）
    var countServer = 0
    var countClient = 0
    //餐车编号（服务端分配1到3）
    var carCode = IDLE
    //回家（服务端设置false，客户端到目的地后设置true）

    //true：停止，false，正在运行
    var isSuspend = false
    //是否在回家的路上（true：回家路上，false：送餐途中）
    var isHoming = true
    //当前位置（客户端设置）
    var currentPosition = HOME
    //目标位置（服务端设置，客户端只能设置为起点）
    var targetPosition = HOME
    //是否正在移动（客户端设置）
    var isMoving = false
    //是否有障碍物（客户端设置）
    var sonicDetect = false
    //是否有餐食（客户端设置）
    var dishDetect = false
    //上次服务端时间（服务端设置）
    var timeServer = 0L

    var timeClient = 0L

    var speed = 30

    @JSONField(serialize = false)
    var ip = ""

    fun copy(carBean: CarBean, autoSend: Boolean = true) {
        isHeartBeat = carBean.isHeartBeat
        countServer = carBean.countServer
        countClient = carBean.countClient
        carCode = carBean.carCode
        isHoming = carBean.isHoming
        currentPosition = carBean.currentPosition
        targetPosition = carBean.targetPosition
        isMoving = carBean.isMoving
        sonicDetect = carBean.sonicDetect
        dishDetect = carBean.dishDetect
        timeClient = carBean.timeClient
        speed = carBean.speed
        if (autoSend) {
            timeServer = carBean.timeServer
        }
        isSuspend = carBean.isSuspend
    }

    override fun toString(): String {
        return "isHeartBeat:" + isHeartBeat + " carCode:" + carCode + " isSuspend:" + isSuspend + " isHoming:" + isHoming + " currentPosition:" + currentPosition + " targetPosition:" + targetPosition +
                " dishDetect:" + dishDetect + " speed:" + speed + " isMoving:" + isMoving
    }
}