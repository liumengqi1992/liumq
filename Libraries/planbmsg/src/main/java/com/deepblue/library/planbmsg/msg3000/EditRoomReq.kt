package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request

class EditRoomReq() : Request(3017, "增删房号") {


    fun add(map_id: Int, room_number: ArrayList<String>): String {
        json = Data(map_id, "add", room_number)
        return toString()
    }

    fun del(map_id: Int, room_number: ArrayList<String>): String {
        json = Data(map_id, "delete", room_number)
        return toString()
    }

    class Data(val map_id: Int, val operate: String, val room_number: ArrayList<String>)
}