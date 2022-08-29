package com.deepblue.library.robotmsg.bean

import android.text.TextUtils

/**
 * 任务格式
 * @param task_id 任务 ID
 * @param task_name 任务名字
 * @param task_map_name 任务所在的地图名字
 * @param task_type 0:区域任务，1:线段任务，2:位点任务，3:录制的任务
 * @param task_body 任务内容
 */
class Task {

    companion object {
        const val NaviType_FreeLine = "free_line"//固定导航
        const val NaviType_FixLine = "fix_line"//自由导航
    }

    var task_name: String = ""
    var task_label: String = ""
        get() {
            if (TextUtils.isEmpty(field)) {
                return task_name
            }
            return field
        }
    var task_map_name: String = ""
    var task_type: Int = 0//0:区域任务，1:线段任务，2:位点任务，3:录制的任务
    var task_body = ArrayList<Body>()//任务内容
    var element_names = ArrayList<String>()//地图元素name

    /**
     * 任务内容
     * @param name 地图元素的名字
     * @param navi_type 导航形式
     */
    class Body {
        var name: String = ""
        var navi_type: String = ""
    }
}