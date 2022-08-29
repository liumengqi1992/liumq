package com.deepblue.library.planbmsg.msg3000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response

/**
 * 从云端下载地图(13011)
 */
class DownloadMapFromCloudRes : Response() {

    init {
        json = Data()
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        //总地图张数
        var map_total_num = 0
        //已下载地图张数
        var map_download_num = 0
        //下载失败地图张数
        var map_fail_num = 0
    }
}