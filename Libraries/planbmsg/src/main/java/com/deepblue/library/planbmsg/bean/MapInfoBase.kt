package com.deepblue.library.planbmsg.bean

import java.io.Serializable

open class MapInfoBase : Serializable {

    //地图ID
    var map_id = 0

    //base64编码后的图片
    var picture = ""
}