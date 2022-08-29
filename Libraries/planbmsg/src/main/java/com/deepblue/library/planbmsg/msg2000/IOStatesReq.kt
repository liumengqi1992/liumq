package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request
import com.deepblue.library.planbmsg.bean.IOState

class IOStatesReq(io_states: ArrayList<IOState>) : Request(2011, "IO控制") {

    init {
        json = Data(io_states)
    }

    /**
     * 数据区
     * @param io_states 控制端口列表
     */
    class Data(val io_states: ArrayList<IOState>)
}