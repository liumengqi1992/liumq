package com.deepblue.library.planbmsg.msg5000

import com.deepblue.library.planbmsg.Request

/**
 * 设置临时值
 */
class SetTempValueReq: Request(5009, "消毒液位设置")  {

    /**
     * 消毒液位设置
     */
    fun liquid(full: Boolean): String {
        json = Liquid(full)
        return toString()
    }

    class Liquid(val liquid_full: Boolean)
}