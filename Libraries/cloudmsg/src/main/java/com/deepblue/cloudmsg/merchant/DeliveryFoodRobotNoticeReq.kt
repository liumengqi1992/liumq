package com.deepblue.cloudmsg.merchant

import com.deepblue.cloudmsg.Request

/**
 * 送餐机器人通知消息（上装APP调用）
 */
class DeliveryFoodRobotNoticeReq(val deviceId: Int, val deviceName: String, val deskNo: String, val noticeType: String) : Request() {
    //TODO 请求参数云端还未改好

    init {
        path = "/robotCommunication/handleDeliveryFoodRobotNotice"
    }
}