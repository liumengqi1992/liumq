package com.deepblue.library.robotmsg.status

import com.deepblue.library.robotmsg.JsonUtils
import com.deepblue.library.robotmsg.Response

/**
 * 查询机器人信息
 */
class InfoRes : Response() {

    companion object {
        const val SHAPE_CIRCLE = "circle"
        const val SHAPE_RECTANGLE = "rectangle"
    }

    init {
        json = Info()
    }

    fun getJson(): Info? {
        return JsonUtils.fromJson(json.toString(), Info::class.java)
    }

    class Info {
        var navi_version: String = ""//导航系统版本
        var hardware_version: String = ""//固件版本
        var netprotocol_version: String = ""//网络协议版本
        var birthplace: String = ""//出生地址
        var birthday: String = ""//出生日期
        var shape: String = ""//形状,”circle”,”rectangle”,如果是”circle”,那么字段 length==width==半径
        var height: Int = 0//高,单位 mm
        var length: Int = 0//长,单位 mm
        var width: Int = 0//宽,单位 mm
        var serial_number: String = ""//序列号,出厂的时候指定
        var model: String = ""//机器型号
    }
}