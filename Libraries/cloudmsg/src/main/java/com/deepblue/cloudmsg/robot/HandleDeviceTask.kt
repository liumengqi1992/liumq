package com.deepblue.cloudmsg.robot

import com.deepblue.cloudmsg.Request
import com.deepblue.cloudmsg.bean.PointArea

class HandleDeviceTask(
    val deviceId: Int,
    val taskName: String,
    val taskType: String,   //任务类型  15
    val taskLoop: String,   //循环次数0
    val taskTiming: String,  //1 立即执行
    val taskPriority: String,// 优先级 4
    val taskMapId: Int,//地图id
    val taskStatus: String, //任务状态 2 正在执行
    val taskPointAreaSeq: ArrayList<PointArea>,  //d点位信息
    val taskPointAreaSeqMap: ArrayList<PointArea>
) :
    Request() {
    init {
        path = "/robotCommunication/handleDeviceTask"
    }
}