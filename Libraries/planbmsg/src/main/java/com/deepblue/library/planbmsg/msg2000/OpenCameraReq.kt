package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class OpenCameraReq : Request(2023,"摄像头开关") {

    fun closeAll(): String {
        json = Data(0)
        return toString()
    }
    fun openAll():String{
        json = Data(3)
        return toString()
    }
    fun openOne():String{
        json = Data(1)
        return toString()
    }
    fun openOther():String{
        json = Data(2)
        return toString()
    }


    class Data(val switch_status: Int)
}