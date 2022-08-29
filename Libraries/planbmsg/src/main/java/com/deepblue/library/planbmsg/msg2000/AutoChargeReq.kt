package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class AutoChargeReq : Request(2010, "自主充电") {

    companion object {
        const val START = 1
        const val FINISH = 2
    }

    fun start(id: Int): String {
        number = START
        json = Data("start", id)
        return toString()
    }

    fun finish(): String {
        number = FINISH
        json = Data("finish")
        return toString()
    }

    /**
     * 数据区
     * @param operate 开始:”start”,结束:”finish”
     * @param id 充电站点名字
     */
    class Data(val operate: String, val id: Int? = null)
}
