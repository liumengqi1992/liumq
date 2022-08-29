package com.deepblue.library.planbmsg.bean

import java.io.Serializable

/**
 * 一般任务格式
 */
class CommonTask :Serializable {

    var task_basic_info = TaskBasicInfo()
    var task_extra_info = TaskExtraInfo()
    var ranges = ArrayList<TaskRange>()

//    //任务路径,通过接口查询任务路径（4007）获取
//    val points = ArrayList<MapPoint>()
}