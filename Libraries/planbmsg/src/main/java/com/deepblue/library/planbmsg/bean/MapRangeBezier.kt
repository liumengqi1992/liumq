package com.deepblue.library.planbmsg.bean

/**
 * 首尾相接的贝塞尔曲线
 */
class MapRangeBezier : MapRange() {

    init {
        //类型.1-区域;2-路径
        range_type = Range_Path
        //工作类型
        work_type = Work_Work
        //区域形状,1-多边形/折线(不包括矩形),2-椭圆/圆,3-贝塞尔曲线,4-矩形
        graph_type = Graph_Bezier
    }
}