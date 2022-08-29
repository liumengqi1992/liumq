package com.deepblue.library.transform

import android.content.Context
import android.graphics.*
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.widget.ImageView
import com.deepblue.library.planbmsg.bean.MapRange
import com.deepblue.library.planbmsg.bean.WayPoint
import com.deepblue.library.utils.BitmapUtils
import org.jetbrains.anko.doAsync

class MapElementView : AppCompatImageView {

    private lateinit var mapManager: MapManager
    private lateinit var imageView: ImageView
    private var isFinished = false
    private val paint = Paint()
    private var bitmapWayPoint: Bitmap? = null

    fun init(mapManager: MapManager, imageView: ImageView) {
        this.mapManager = mapManager
        this.imageView = imageView

        bitmapWayPoint = BitmapUtils.getBitmapFromVectorDrawable(
            context,
            R.drawable.transform_ic_waypoint_func
        )

        doAsync {
            while (!isFinished) {
                if (mapManager.isMatrixChanged()) {
                    invalidate()
                }
                mapManager.setMatrixValues()
//                Thread.sleep(1)
            }
        }
    }

    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        paint.isAntiAlias = true
    }

    fun destory() {
        isFinished = true
    }

    override fun onDraw(canvas: Canvas?) {
        mapManager.getMatrix()
        super.onDraw(canvas)

        paint.color = Color.RED
        val lefttop = getLeftTop()
        canvas?.drawCircle(lefttop[0], lefttop[1], 20F, paint)

        paint.color = Color.GREEN
        val righttop = getRightTop()
        canvas?.drawCircle(righttop[0], righttop[1], 20F, paint)

        paint.color = Color.BLUE
        val leftbottom = getLeftBottom()
        canvas?.drawCircle(leftbottom[0], leftbottom[1], 20F, paint)

        paint.color = Color.YELLOW
        val rightbottom = getRightBottom()
        canvas?.drawCircle(rightbottom[0], rightbottom[1], 20F, paint)

//        if (bitmapWayPoint == null || bitmapWayPoint?.isRecycled == true || bitmapWayPoint?.width?:0 <= 0 || bitmapWayPoint?.height?:0 <= 0) {
//            return
//        }
//        val map = mapManager.map
//        for (i in map.points.indices) {
//            val wayPoint = map.points[i]
//            mapManager.drawBitmap(canvas, bitmapWayPoint!!, wayPoint, wayPoint.angle)
//        }

        elementToCanvas(canvas, MapManager.PaintText)
    }

    /**
     * 地图元素中使用图片外的元素（包括地图元素的名称）
     */
    private fun elementToCanvas(canvas: Canvas?, paintText: Paint) {
        val map = mapManager.map
        if (map.ranges.isNotEmpty()) {
            paint.strokeCap = Paint.Cap.ROUND
            if (map.ranges.isNotEmpty()) {
                for (i in 0 until map.ranges.size) {
                    val range = map.ranges[i]
                    paintMapRangeName(canvas, range, paintText)
                }
            }
        }

        for (i in 0 until map.points.size) {
            val wayPoint = map.points[i]
            if (wayPoint.real != WayPoint.Real_Point) {
                continue
            }
            mapManager.paintMapWayPoint(
                canvas,
                wayPoint,
                paintText
            )
        }
    }

    /**
     * 绘制地图range名称
     */
    private fun paintMapRangeName(
        canvas: Canvas?,
        polygon: MapRange,
        paintText: Paint
    ) {

        if (polygon.point_info.isEmpty()) {
            return
        }
        val mapPoints = mapManager.mapCoordinateToCanvas(polygon.point_info[0])
        val text = polygon.name
        canvas?.drawText(
            text,
            (mapPoints.x + MapManager.TextOffsetX).toFloat(),
            (mapPoints.y + MapManager.TextOffsetX).toFloat(),
            paintText
        )
    }

    private fun getLeftTop(): FloatArray {
        return mapManager.transferMapPointToFloatArray(mapManager.getCanvasCoordinate(0, 0))
    }

    private fun getRightTop(): FloatArray {
        val width = mapManager.getMapBitmapWidth()
        return mapManager.transferMapPointToFloatArray(mapManager.getCanvasCoordinate(width, 0))
    }

    private fun getLeftBottom(): FloatArray {
        val height = mapManager.getMapBitmapHeight()
        return mapManager.transferMapPointToFloatArray(mapManager.getCanvasCoordinate(0, height))
    }

    private fun getRightBottom(): FloatArray {
        val width = mapManager.getMapBitmapWidth()
        val height = mapManager.getMapBitmapHeight()
        return mapManager.transferMapPointToFloatArray(mapManager.getCanvasCoordinate(width, height))
    }
}