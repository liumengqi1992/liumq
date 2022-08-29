package com.deepblue.library.planbmsg.bean

import java.io.Serializable

class TaskPoint : AngleMapPoint() ,Serializable{

    //点位ID，新增填0
    var id = 0
    //任务id，新建填0
    var task_id = 0
    //地图ID
    var map_id = 0
    //本体地图point_id
    var map_point_id = 0
    //点类型 引导点：N 导航点：P 充电点：C 电梯点：L 前台点：G 房间点：R
    val point_type = ArrayList<String>()
    //点位名称
    var point_name = ""
}