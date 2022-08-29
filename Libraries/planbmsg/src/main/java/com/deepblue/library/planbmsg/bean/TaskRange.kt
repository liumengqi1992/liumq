package com.deepblue.library.planbmsg.bean

import com.alibaba.fastjson.annotation.JSONField
import java.io.Serializable

class TaskRange : Serializable {

    companion object {
        //区域
        const val TASK_RANGE_TYPE_AREA = 1
        //路径
        const val TASK_RANGE_TYPE_PATH = 2
        //点
        const val TASK_RANGE_TYPE_POINT = 3

        //导航模式
        const val WORK_PATTERN_NAVIGATION = 0
        //标准模式
        const val WORK_PATTERN_STANDARD = 1
        //干吸模式
        const val WORK_PATTERN_DRY = 2
        //省水模式
        const val WORK_PATTERN_WATER = 3
        //深度模式
        const val WORK_PATTERN_DEEP = 4
    }

    //rangeID
    var map_range_id = 0
    //任务id，新建填0
    var task_id = 0
    //地图ID
    var map_id = 0
    //工作区名称
    var task_range_name = ""
    //0-导航模式，1-标准模式，2-干吸模式，3-省水模式，4-深度模式
    var work_pattern = 0
    //工作区类型 1：区域 2：路径 3：点
    var task_range_type = 0

    //以下是查询返回特有的数据
    //rangeID
    var id = 0

    var delay_seconds=3
    //若为路径-- 0:默认 1：折线  2:贝塞尔曲线  若为区域-- 0：默认 1:椭圆 2:矩形 3:多边形
    //区域形状,1-多边形/折线(不包括矩形),2-椭圆/圆,3-贝塞尔曲线,4-矩形
    var graph_range_type = 0
    val points = ArrayList<TaskPoint>()

    @JSONField(serialize = false)
    var isFocused = false

    @JSONField(serialize = false)
    var checked = true

    //查询区域内的实际运动路径（4006）
    @JSONField(serialize = false)
    var pathPoints = ArrayList<MapPoint>()
}