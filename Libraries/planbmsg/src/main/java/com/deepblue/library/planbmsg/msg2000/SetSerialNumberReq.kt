package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class SetSerialNumberReq(serial_number: String) : Request(2012, "写入序列号") {

    init {
        json = Data(serial_number)
    }

    /**
     * 数据区
     * @param serial_number 序列号
     */
    class Data(val serial_number: String)
}