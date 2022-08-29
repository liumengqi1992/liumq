package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.Request

class CopyMapsReq(map_id_list: ArrayList<Int>, map_name_list: ArrayList<String>) : Request(3014, "拷贝机器人上的地图") {

    init {
        json = Data(map_id_list, map_name_list)
    }

    /**
     * 数据区
     * @param map_id_list 所有需要拷贝的地图id
     * @param map_name_list 所有拷贝之后的地图名
     */
    class Data(val map_id_list: ArrayList<Int>, val map_name_list: ArrayList<String>)
}
