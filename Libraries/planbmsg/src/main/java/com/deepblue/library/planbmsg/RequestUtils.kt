package com.deepblue.library.planbmsg

import com.deepblue.library.planbmsg.bean.RobotLoc
import com.deepblue.library.planbmsg.bean.Map
import com.deepblue.library.planbmsg.bean.MapEdit
import com.deepblue.library.planbmsg.bean.WayPoint
import com.deepblue.library.planbmsg.msg3000.DownloadMapReq
import com.deepblue.library.planbmsg.msg3000.UploadMapReq

/**
 * 组织请求报文
 */
object RequestUtils {

    fun downloadMap(mapId: Int): String {
        return DownloadMapReq().map(mapId)
    }

    /**
     * 添加点
     */
    fun addPoint(map: Map, robotLoc: RobotLoc, name: String, type: String): Request {
        val mapEdit = MapEdit()
        val point = WayPoint()
        point.name = name
        point.x = robotLoc.x
        point.y = robotLoc.y
        point.angle = robotLoc.angle
        point.real = WayPoint.Real_Point
        point.map_id = map.map_info.map_id
        point.type.add(type)
        mapEdit.points.add(point)
        return UploadMapReq(mapEdit, UploadMapReq.ACTION_ADD)
    }

    fun addPoints(map: Map, points: ArrayList<WayPoint>, type: String): Request {
        val mapEdit = MapEdit()
        for (p in points.indices) {
            val point = WayPoint()
            point.name = "P${p}"
            point.x = points[p].x
            point.y = points[p].y
            point.angle = points[p].angle
            point.real = WayPoint.Real_Point
            point.map_id = map.map_info.map_id
            point.type.add(type)
            mapEdit.points.add(point)
        }

        return UploadMapReq(mapEdit, UploadMapReq.ACTION_ADD)
    }

    /**
     * 修改点
     */
    fun modifyPoint(map: Map, pointId: Int, name: String): Request {
        val mapEdit = MapEdit()
        for (i in map.points.indices) {
            val point = map.points[i]
            if (point.point_id == pointId) {
                point.name = name
                mapEdit.points.add(point)
                break
            }
        }
        return UploadMapReq(mapEdit, UploadMapReq.ACTION_MODIFY)
    }

    /**
     * 删除点
     */
    fun deletePoint(map: Map, pointId: Int): Request {
        val mapEdit = MapEdit()
        mapEdit.points_del.add(pointId)
        return UploadMapReq(mapEdit, UploadMapReq.ACTION_DELETE)
    }

    fun deletePoints(map: Map, points: ArrayList<Int>): Request {
        val mapEdit = MapEdit()
        for (point in points) {
            mapEdit.points_del.add(point)
        }
        return UploadMapReq(mapEdit, UploadMapReq.ACTION_DELETE)
    }
}