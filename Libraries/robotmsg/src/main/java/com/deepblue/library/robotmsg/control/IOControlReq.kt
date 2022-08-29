package com.deepblue.library.robotmsg.control

import com.deepblue.library.robotmsg.Request

/**
 * IO 控制
 */
class IOControlReq(portNames: Array<String>, values: Array<Boolean>) : Request(2012, "robot_control_iocontrol_req") {

    init {
        json = Data(portNames, values)
    }

    class Data(portNames: Array<String>, values: Array<Boolean>) {
        val io_states = ArrayList<State>()

        init {
            val size = Math.min(portNames.size, values.size)
            io_states.clear()
            for (i in 0 until size) {
                val state = State(portNames[i], values[i])
                io_states.add(state)
            }
        }
    }

    class State(val port_name: String, val value: Boolean)
}