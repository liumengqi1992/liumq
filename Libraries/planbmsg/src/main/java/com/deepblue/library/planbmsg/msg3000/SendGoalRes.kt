package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response

/**
 * 发送自由导航目标点(13001)
 */
class SendGoalRes : Response() {

    companion object {
        //准备前往
        private const val STATUS_RECEIVE = "receive"
        //到达目标点
        private const val STATUS_ARRIVE = "arrive"
        //无法到达目标点
        private const val STATUS_ERROR = "error"
    }

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        var status = ""

        fun isArrived(): Boolean {
            return STATUS_ARRIVE == status
        }

        fun isError(): Boolean {
            return STATUS_ERROR == status
        }

        fun isReceived(): Boolean {
            return STATUS_RECEIVE == status
        }
    }
}