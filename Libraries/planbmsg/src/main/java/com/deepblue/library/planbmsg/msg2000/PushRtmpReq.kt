package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class PushRtmpReq : Request(2036,"rtmp开关"){

    fun open(number:Int):String{
        json = Data(1,number)
        return toString()
    }
    fun close(number:Int):String{
        json = Data(0,number)
        return toString()
    }


    class Data(val push_switch: Int,val video_number:Int)
}