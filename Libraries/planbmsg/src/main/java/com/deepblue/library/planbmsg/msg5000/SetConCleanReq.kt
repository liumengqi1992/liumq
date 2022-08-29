package com.deepblue.library.planbmsg.msg5000

import com.deepblue.library.planbmsg.Request

class SetConCleanReq: Request(5006, "设置洗地机连扫")  {

    fun con(): String {
        json = Data(true)
        return toString()
    }

    fun disCon():String{
        json = Data(false)
        return toString()
    }

    class Data(val work_betten_range: Boolean)

}