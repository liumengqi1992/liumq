package com.deepblue.library.planbmsg.bean

import com.alibaba.fastjson.annotation.JSONField

class RangePoint : AngleMapPoint() {

    companion object {
        //未修改
        const val TYPE_UNMODIFY = 1
        //修改
        const val TYPE_MODIFY = 2
        //新增
        const val TYPE_NEW = 3
    }

    var point_id = 0
    //坐标类型。1-gis;2-点云
    var coordinates_type = 2
    // 1-未修改，即该点的信息保持不变，其他值正常填写，type填写1;
    // 2-修改，其他值填写新值，type填写2;
    // 3-新增，该点的point_id写0，其他值填写新值，type填写3。
    var type = TYPE_NEW

    @JSONField(serialize = false)
    var name = ""
}