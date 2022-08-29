package com.deepblue.libraries

import android.graphics.PointF
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.bean.MapPoint
import com.deepblue.library.planbmsg.bean.WayPoint
import com.deepblue.library.planbmsg.msg3000.DownloadMapRes
import com.deepblue.library.transform.MapManager
import com.deepblue.library.utils.BitmapUtils
import com.deepblue.library.transform.Listener
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {

    companion object {
        const val MAP_JSON = "{\"error_code\":0,\"json\":{\"map_info\":{\"map_id\":3,\"map_name\":\"testmap\",\"max_pos\":{\"x\":5.2858132980763912,\"y\":3.9699687901884317},\"min_pos\":{\"x\":-11.574186325073242,\"y\":-16.760030746459961},\"picture\":\"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\",\"resolution\":0.029999999329447746},\"points\":[{\"angle\":45,\"coordinates_type\":2,\"name\":\"P1\",\"point_id\":7,\"real\":1,\"type\":[\"C\",\"E\",\"D\",\"H\"],\"x\":0.52075542118529938,\"y\":-0.13106535886896609},{\"angle\":90,\"coordinates_type\":2,\"name\":\"\",\"point_id\":8,\"real\":0,\"type\":[\"P\"],\"x\":-0.75509415967223958,\"y\":0.16899391481969417},{\"angle\":0,\"coordinates_type\":2,\"name\":\"\",\"point_id\":9,\"real\":0,\"type\":[\"P\"],\"x\":2.4970749570892394,\"y\":0.51906363966104152},{\"angle\":0,\"coordinates_type\":2,\"name\":\"\",\"point_id\":10,\"real\":0,\"type\":[\"P\"],\"x\":2.0968082424030854,\"y\":-0.68117459950275361},{\"angle\":0,\"coordinates_type\":2,\"name\":\"\",\"point_id\":11,\"real\":0,\"type\":[\"P\"],\"x\":-0.68004472287316275,\"y\":-0.75618924626354556},{\"angle\":0,\"coordinates_type\":2,\"name\":\"\",\"point_id\":12,\"real\":0,\"type\":[\"P\"],\"x\":-4.9328824574046735,\"y\":0.61908408420275407},{\"angle\":0,\"coordinates_type\":2,\"name\":\"\",\"point_id\":13,\"real\":0,\"type\":[\"P\"],\"x\":-1.555628504571871,\"y\":0.1439894903297585},{\"angle\":0,\"coordinates_type\":2,\"name\":\"\",\"point_id\":14,\"real\":0,\"type\":[\"P\"],\"x\":-2.3811796335803024,\"y\":-1.1062594288685546},{\"angle\":0,\"coordinates_type\":2,\"name\":\"\",\"point_id\":15,\"real\":0,\"type\":[\"P\"],\"x\":-4.8328153209694733,\"y\":-0.75619016179086884}],\"ranges\":[{\"graph_type\":1,\"name\":\"G1\",\"point_id\":[8,9,10,11],\"range_id\":2,\"range_type\":1,\"work_type\":1},{\"graph_type\":1,\"name\":\"G2\",\"point_id\":[12,13,14,15],\"range_id\":3,\"range_type\":1,\"work_type\":1}]},\"type\":13009}"
    }

    private var currentPos: WayPoint? = null
    private lateinit var mapManager: MapManager

    override fun onDestroy() {
        mapElementView.destory()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val res = JsonUtils.fromJson(MAP_JSON, DownloadMapRes::class.java) ?: return
        val map = res.getJson() ?: return
        MapManager.initMap(map)

        val mapBitmap = MapManager.mapToBitmap(map) ?: return

        mapManager = MapManager(map, mapBitmap, tansformativeImageView)
        setBitmaps(mapManager)

        val elementMapBitmap = mapManager.elementToBitmap(this, mapBitmap)

        tansformativeImageView.setImageBitmap(elementMapBitmap)

        mapElementView.init(mapManager, tansformativeImageView)

        tansformativeImageView.setListener(object : Listener {
            override fun onClick(pointF: PointF) {
            }

            override fun onLongClick(pointF: PointF) {
            }

            override fun onTouched(pointF: PointF) {
                val found = mapManager.findFocusedElement(pointF)
                currentPos = if (found) {
                    tansformativeImageView.canSingleFingerMove = false
                    mapManager.findFocusedPos(pointF)
                } else {
                    tansformativeImageView.canSingleFingerMove = true
                    onMapInvalidate()
                    null
                }
            }

            override fun onDoubleClick(pointF: PointF) {
            }

            override fun onMove(pointF: PointF) {
//                mapElementView.mapImageMatrix = tansformativeImageView.imageMatrix
//                val imageMatrix = tansformativeImageView.imageMatrix
//                val values = FloatArray(9)
//                imageMatrix.getValues(values)
//                KLog.d("imageMatrix", "MTRANS_X: ${values[Matrix.MTRANS_X]}")
//                KLog.d("imageMatrix", "MTRANS_Y: ${values[Matrix.MTRANS_Y]}")
//                KLog.d("imageMatrix", "------------------------------------")
                if (currentPos != null) {
                    tansformativeImageView.canSingleFingerMove = false

                    val point = MapPoint()
                    point.x = pointF.x.toDouble()
                    point.y = pointF.y.toDouble()
                    val pos = mapManager.pointerToMap(point) ?: return
                    currentPos?.x = pos.x
                    currentPos?.y = pos.y
                    currentPos?.isChanged = true

                    if (mapManager.onChangeOver(currentPos!!)) {
                        onMapInvalidate()
                    }

                } else {
                    tansformativeImageView.canSingleFingerMove = true
                }
            }

            override fun onMoveOver(pointF: PointF) {
//                mapElementView.mapImageMatrix = tansformativeImageView.imageMatrix
            }

            override fun onScaleChanged(scale: Float) {
//                mapElementView.mapImageMatrix = tansformativeImageView.imageMatrix
            }
        })
    }

    private fun onMapInvalidate() {

//        setBitmaps(mapManager)

        val mapBitmap = MapManager.mapToBitmap(mapManager.map) ?: return
        val elementMapBitmap = mapManager.elementToBitmap(this, mapBitmap)

        tansformativeImageView.setImageBitmap(elementMapBitmap)

        mapElementView.invalidate()
    }

    private fun setBitmaps(mapManager: MapManager) {
        mapManager.setBitmaps(MapManager.TYPE_POINT_NAV, BitmapUtils.getBitmapFromVectorDrawable(
            this, R.drawable.transform_ic_waypoint_nav
        ))
        mapManager.setBitmaps(MapManager.TYPE_POINT_NAV_FOCUSED, BitmapUtils.getBitmapFromVectorDrawable(
            this, R.drawable.transform_ic_waypoint_nav_focused
        ))
        mapManager.setBitmaps(MapManager.TYPE_POINT_FUNC, BitmapUtils.getBitmapFromVectorDrawable(
            this, R.drawable.transform_ic_waypoint_func
        ))
        mapManager.setBitmaps(MapManager.TYPE_POINT_FUNC_FOCUSED, BitmapUtils.getBitmapFromVectorDrawable(
            this, R.drawable.transform_ic_waypoint_func_focused
        ))
        mapManager.setBitmaps(MapManager.TYPE_POINT_RELOC, BitmapUtils.getBitmapFromVectorDrawable(
            this, R.drawable.transform_ic_waypoint_reloc
        ))
        mapManager.setBitmaps(MapManager.TYPE_POINT_START, BitmapUtils.getBitmapFromVectorDrawable(
            this, R.drawable.transform_startpoint
        ))

        mapManager.setNineBitmaps(MapManager.TYPE_POINT_MESSAGE, BitmapUtils.getBitmapFormResources(
            this, R.drawable.transform_msgframe))
        mapManager.setNineBitmaps(MapManager.TYPE_POINT_TEXT_UP, BitmapUtils.getBitmapFormResources(
            this, R.drawable.transform_wpbg))
        mapManager.setNineBitmaps(MapManager.TYPE_POINT_TEXT_DOWN, BitmapUtils.getBitmapFormResources(
            this, R.drawable.transform_wpbg_down))
        mapManager.setNineBitmaps(MapManager.TYPE_POINT_TEXT_LEFT, BitmapUtils.getBitmapFormResources(
            this, R.drawable.transform_wpbg_left))
        mapManager.setNineBitmaps(MapManager.TYPE_POINT_TEXT_RIGHT, BitmapUtils.getBitmapFormResources(
            this, R.drawable.transform_wpbg_right))
    }
}