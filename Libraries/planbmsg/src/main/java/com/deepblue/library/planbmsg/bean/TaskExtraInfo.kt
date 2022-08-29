package com.deepblue.library.planbmsg.bean

import java.io.Serializable

class TaskExtraInfo : Serializable {

    companion object {
        //未设置
        const val TASK_WAY_UNSET = 0
        //往返
        const val TASK_WAY_RETURN = 1
        //转圈
        const val TASK_WAY_TURN = 2

        //不播放
        const val VIDEO_TYPE_NO = 0
        //音频
        const val VIDEO_TYPE_AUDIO = 1
        //文字
        const val VIDEO_TYPE_TEXT = 2

        //距离间隔
        const val BROADCAST_MODE_DISTANCE = 1
        //时间间隔
        const val BROADCAST_MODE_TIME = 2
    }

    //任务额外信息id，新建填0
    var id = 0
    //任务id，新建填0
    var task_id = 0
    //任务方式 0:未设置 1：往返 2：转圈
    var task_way = 0
    //返回点ID
    var reback_id = 0
    //0-导航模式，1-标准模式，2-干吸模式，3-省水模式，4-深度模式
    var work_pattern = 0

    //以下是查询的数据
    val reback_info = TaskPoint()
    //语音播放传输类型 0：不播放 1：音频 2：文字
    var videoType = 0
    //语音音频url地址或文字
    var videoURL = ""
    //播放模式 1：距离间隔 2：时间间隔
    var broadcastMode = 0
    //间隔值  单位可能是（m | 米）或者（s | 秒）
    var broadcastInterval = 0
    //音量  0 - 100的范围表示从0%--100 %
    var volume = 0
    //执行补货时间
    var replenish_time = ""
    //目的地等待时间,分钟
    var des_wait_time = 0
}