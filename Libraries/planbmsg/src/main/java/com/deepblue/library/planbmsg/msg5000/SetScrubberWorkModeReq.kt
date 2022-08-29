package com.deepblue.library.planbmsg.msg5000

import com.deepblue.library.planbmsg.Request

class SetScrubberWorkModeReq : Request(5000, "设置洗地机工作模式") {

    companion object {
        const val AUTO = 1
        const val MANUAL = 2

        const val MODE_AUTO = "auto"
        const val MODE_MANUAL = "manual"
    }

    fun auto(): String {
        number = AUTO
        json = Data(MODE_AUTO)
        return toString()
    }

    fun manual(): String {
        number = MANUAL
        json = Data(MODE_MANUAL)
        return toString()
    }

    /**
     * 数据区
     * @param mode 工作模式
     */
    class Data(val mode: String)
}