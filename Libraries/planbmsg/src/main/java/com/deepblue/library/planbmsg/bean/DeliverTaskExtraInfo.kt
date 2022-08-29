package com.deepblue.library.planbmsg.bean

class DeliverTaskExtraInfo {

    //送货任务额外信息id
    var id = 0
    //任务ID，新建填0
    var task_id = 0
    //物品信息
    var goodsInfo = ""
    //物品数量
    var goodsNo = 0
    var points = ArrayList<TaskPoint>()
    //出货人手机号
    var sendPhoneNo = ""
    //收货人手机号
    var recvPhoneNo = ""
    //还货人手机号
    var backPhoneNo = ""
}