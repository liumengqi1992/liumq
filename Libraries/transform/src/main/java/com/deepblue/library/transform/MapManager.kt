package com.deepblue.library.transform

import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.util.Base64
import android.widget.ImageView
import com.deepblue.library.planbmsg.bean.*
import com.deepblue.library.planbmsg.bean.Map
import com.deepblue.library.utils.BitmapUtils
import com.deepblue.library.utils.ResourcesUtils
import java.io.ByteArrayInputStream
import java.io.IOException
import java.lang.ref.SoftReference
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.sqrt

class MapManager(val map: Map, private val bitmapMap: Bitmap, val imageView: ImageView) {

    companion object {
        private const val InDistance = 1.0
        private const val Has_Mask = true
        private val BitmapConfig = Bitmap.Config.ARGB_8888
        private const val Flags = Base64.NO_WRAP
        const val TextOffsetX = 5
        private const val TextOffsetY = 30
        private const val LineWidth = 5F
        private const val PathWidth = 2F
        private const val LaserWidth = 5F
        private const val RulerWidth = 10F
        const val TextSize = 30F
        private const val CircleRadius = 18F
        private const val ForbiddenLineWidth = 3F

        private val PaintBitmap = Paint(Paint.ANTI_ALIAS_FLAG)
        val PaintText = Paint(Paint.ANTI_ALIAS_FLAG)

        //普通位点
        const val TYPE_POINT_NAV_FOCUSED = 1
        const val TYPE_POINT_NAV = 2
        //特殊位点
        const val TYPE_POINT_FUNC_FOCUSED = 3
        const val TYPE_POINT_FUNC = 4
        //定位位点
        const val TYPE_POINT_RELOC = 5
        //位点名称背景
        const val TYPE_POINT_TEXT_UP = 6
        const val TYPE_POINT_TEXT_DOWN = 7
        const val TYPE_POINT_TEXT_LEFT = 8
        const val TYPE_POINT_TEXT_RIGHT = 9
        //测距起点
        const val TYPE_POINT_START = 10
        //测距文字背景
        const val TYPE_POINT_MESSAGE = 11

        private fun byteToBitmap(imgByte: ByteArray, sampleSize: Int): Bitmap? {
            val options = BitmapFactory.Options()
            options.inSampleSize = sampleSize
            val input = ByteArrayInputStream(imgByte)
            val softRef = SoftReference(
                BitmapFactory.decodeStream(
                    input, null, options
                )
            )
            val bitmap = softRef.get()
            try {
                input.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return bitmap
        }

        fun mapToBitmap(map: Map, width: Int = 0, height: Int = 0): Bitmap? {
            val newBitmap: Bitmap
            try {
                val bitmapArray = Base64.decode(map.map_info.picture, Flags)
                val distanceWidth = map.map_info.max_pos.x - map.map_info.min_pos.x
                val distanceHeight = map.map_info.max_pos.y - map.map_info.min_pos.y
                if (map.resolution == 0.0) {
                    map.resolution = map.map_info.resolution
                }
                val distance = max(distanceWidth, distanceHeight) / map.resolution
                val bitmap: Bitmap
                bitmap = if (distance < 2048) {
                    BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.size)
                } else {
                    val sampleSize: Int = (distance / 2048).toInt()
                    byteToBitmap(bitmapArray, sampleSize) ?: return null
                }

                newBitmap = if (width > 0 && height > 0) {
                    val scaleWidth = width.toFloat() / bitmap.width
                    val scaleHeight = height.toFloat() / bitmap.height
                    //                m.postScale(scaleWidth, -scaleHeight)
                    val m = Matrix()
                    m.setScale(scaleWidth, scaleHeight)
                    Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)
                } else {
                    //                m.postScale(1f, -1f)
                    bitmap.copy(BitmapConfig, true)
                }

//            newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)

                if (Has_Mask) {
                    val canvas = Canvas(newBitmap)

                    val paint = Paint(Paint.ANTI_ALIAS_FLAG)//消除锯
                    paint.style = Paint.Style.FILL_AND_STROKE
                    paint.color = Color.parseColor("#4C0E0E1E")
                    canvas.drawRect(
                        0f,
                        0f,
                        newBitmap.width.toFloat(),
                        newBitmap.height.toFloat(),
                        paint
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
            return newBitmap
        }

        fun initMap(map: Map) {
            if (map.ranges.isEmpty()) {
                return
            }
            for (i in 0 until map.ranges.size) {
                val range = map.ranges[i]
                if (range.point_id.isEmpty()) {
                    continue
                }
                if (range.point_info.isNotEmpty()) {
                    continue
                }
                for (j in 0 until range.point_id.size) {
                    val rangePoint =
                        findRangePointFromPoints(range.point_id[j], map.points)
                            ?: continue
                    range.point_info.add(rangePoint)
                }
            }
        }

        private fun findRangePointFromPoints(id: Int, points: ArrayList<WayPoint>): RangePoint? {
            if (points.isEmpty()) {
                return null
            }
            for (i in 0 until points.size) {
                val point = points[i]
                if (point.point_id == id) {
                    val rangePoint = RangePoint()
                    rangePoint.point_id = point.point_id
                    rangePoint.x = point.x
                    rangePoint.y = point.y
                    rangePoint.angle = point.angle
                    rangePoint.coordinates_type = point.coordinates_type
                    rangePoint.type = RangePoint.TYPE_UNMODIFY
                    rangePoint.name = point.name
                    return rangePoint
                }
            }
            return null
        }
    }

    init {

        PaintText.textSize = TextSize
        PaintText.color = Color.WHITE
        PaintText.style = Paint.Style.FILL
        PaintText.strokeCap = Paint.Cap.ROUND
    }

    private val values = FloatArray(9)
    private val lastValues = FloatArray(9)
    private val hmBitmaps = ConcurrentHashMap<Int, Bitmap>()
    private val hmNineBitmaps = ConcurrentHashMap<Int, Bitmap>()

    fun setBitmaps(type: Int, bitmap: Bitmap) {
        hmBitmaps[type] = bitmap
    }

    fun setNineBitmaps(type: Int, bitmap: Bitmap) {
        hmNineBitmaps[type] = bitmap
    }

    fun isMatrixChanged(): Boolean {
        if (lastValues[Matrix.MTRANS_X] != values[Matrix.MTRANS_X] || lastValues[Matrix.MTRANS_Y] != values[Matrix.MTRANS_Y]) {
            return true
        }
        return false
    }

    fun setMatrixValues() {
        val matrix = imageView.imageMatrix
        matrix.getValues(lastValues)
    }

    fun getMatrix(): Matrix {
        val matrix = imageView.imageMatrix
        matrix.getValues(values)
        return matrix
    }

    fun getMapBitmapWidth(): Int {
        return bitmapMap.width
    }

    fun getMapBitmapHeight(): Int {
        return bitmapMap.height
    }

    private fun getMatrixAngle(): Float {
        val radians = atan2(
            values[Matrix.MSKEW_X].toDouble(),
            values[Matrix.MSCALE_X].toDouble()
        )
        return Math.toDegrees(radians).toFloat()
    }

    /**
     * Bitmap中的坐标转Canvas坐标
     */
    private fun getCanvasCoordinate(bitmapXY: MapPoint): MapPoint {
        val mp = MapPoint()
        mp.x = values[Matrix.MSCALE_X] * bitmapXY.x + values[Matrix.MSKEW_X] * bitmapXY.y + values[Matrix.MTRANS_X]
        mp.y = values[Matrix.MSKEW_Y] * bitmapXY.x + values[Matrix.MSCALE_Y] * bitmapXY.y + values[Matrix.MTRANS_Y]
        return mp
    }

    fun getCanvasCoordinate(x: Int, y: Int): MapPoint {
        val mp = MapPoint()
        mp.x = x.toDouble()
        mp.y = y.toDouble()
        return getCanvasCoordinate(mp)
    }

    /**
     * 地图坐标转Bitmap坐标
     */
    private fun mapCoordinateToBitmap(mapPoint: MapPoint): MapPoint {
        val mp = MapPoint()

        val minPoint = map.map_info.min_pos
        val maxPoint = map.map_info.max_pos
        val w = bitmapMap.width
        val h = bitmapMap.height

        mp.x = w * (mapPoint.x - minPoint.x) / (maxPoint.x - minPoint.x)
        mp.y = h * (maxPoint.y - mapPoint.y) / (maxPoint.y - minPoint.y)
        return mp
    }

    fun transferMapPointToFloatArray(mapPoint: MapPoint): FloatArray {
        val fa = FloatArray(2)
        fa[0] = mapPoint.x.toFloat()
        fa[1] = mapPoint.y.toFloat()
        return fa
    }

    fun mapCoordinateToCanvas(mapPoint: MapPoint): MapPoint {
        val mp = mapCoordinateToBitmap(mapPoint)
        return getCanvasCoordinate(mp)
    }

    private fun mapCoordinateToCanvasFloat(mapPoint: MapPoint): FloatArray {
        val mp = mapCoordinateToBitmap(mapPoint)
        return transferMapPointToFloatArray(getCanvasCoordinate(mp))
    }

    private fun paintBrushes(
        canvas: Canvas,
        brushes: ArrayList<ArrayList<Map.Brush>>?
    ) {

        if (brushes == null || brushes.isEmpty()) {
            return
        }
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)//消除锯
        paint.style = Paint.Style.STROKE
        for (i in 0 until brushes.size) {
            val brush = brushes[i]
            if (brush.isNotEmpty()) {
                if (brush[0].radius <= 0F) {
                    continue
                }
                val path = Path()
                path.moveTo(brush[0].x, brush[0].y)
                paint.color = brush[0].color
                paint.strokeWidth = brush[0].radius
                for (j in 1 until brush.size) {
                    path.lineTo(brush[j].x, brush[j].y)
                }
                canvas.drawPath(path, paint)
            }
        }
    }

    private fun distance(point1: MapPoint, point2: MapPoint): Double {
        val dx = point2.x - point1.x
        val dy = point2.y - point1.y
        return sqrt(dx * dx + dy * dy)
    }

    private fun getColor(context: Context, rangeType: Int, workType: Int): Int {
        val defValue = R.color.default_area
        val type = "range${rangeType}work$workType"
        return ResourcesUtils.getColorId(context, type, defValue)
    }

    /**
     * 绘制多边形
     */
    private fun paintMapPolygon(
        isPolygon: Boolean,
        canvas: Canvas,
        polygon: MapRange,
        paint: Paint
    ) {

        var firstPoint: MapPoint? = null
        val path = Path()
        for (k in 0 until polygon.point_info.size) {
            val mapPoints = mapCoordinateToBitmap(polygon.point_info[k])
            if (k == 0) {
                firstPoint = mapPoints
                path.moveTo(mapPoints.x.toFloat(), mapPoints.y.toFloat())
            } else {
                path.lineTo(mapPoints.x.toFloat(), mapPoints.y.toFloat())
                if (isPolygon && k == polygon.point_info.size - 1) {
                    path.lineTo(firstPoint!!.x.toFloat(), firstPoint.y.toFloat())
                }
            }
        }
        canvas.drawPath(path, paint)

        if (polygon.isFocused) {
            for (k in 0 until polygon.point_info.size) {
                val mapPoints = mapCoordinateToBitmap(polygon.point_info[k])
                drawCircle(canvas, mapPoints, paint)
            }
        }
    }

    /**
     * 绘制地图贝塞尔曲线
     */
    private fun paintMapBezier(
        context: Context,
        canvas: Canvas,
        bezier: MapRange,
        startIndex: Int,
        paint: Paint
    ) {

        if (bezier.point_info.isEmpty() || startIndex < 0 || startIndex + 3 >= bezier.point_info.size) {
            return
        }

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = LineWidth

        paint.color = ResourcesUtils.getColor(context, R.color.map_line)
        if (bezier.isFocused) {
            paint.color = ResourcesUtils.getColor(context, R.color.focused_line)
        }
        val startPoint = mapCoordinateToCanvas(bezier.point_info[startIndex])
        val controlPoint0 = mapCoordinateToCanvas(bezier.point_info[startIndex + 1])
        val controlPoint1 = mapCoordinateToCanvas(bezier.point_info[startIndex + 2])
        val endPoint = mapCoordinateToCanvas(bezier.point_info[startIndex + 3])

        val path = Path()
        path.moveTo(startPoint.x.toFloat(), startPoint.y.toFloat())
        path.cubicTo(
            controlPoint0.x.toFloat(),
            controlPoint0.y.toFloat(),
            controlPoint1.x.toFloat(),
            controlPoint1.y.toFloat(),
            endPoint.x.toFloat(),
            endPoint.y.toFloat()
        )
        canvas.drawPath(path, paint)

        if (bezier.isFocused) {
            drawCircle(canvas, controlPoint0, paint)
            drawCircle(canvas, controlPoint1, paint)
            paint.color = Color.BLACK
            paint.strokeWidth = 1F
            canvas.drawLine(
                startPoint.x.toFloat(),
                startPoint.y.toFloat(),
                controlPoint0.x.toFloat(),
                controlPoint0.y.toFloat(),
                paint
            )
            canvas.drawLine(
                endPoint.x.toFloat(),
                endPoint.y.toFloat(),
                controlPoint1.x.toFloat(),
                controlPoint1.y.toFloat(),
                paint
            )
        }
    }

    private fun drawNineBitmap(canvas: Canvas?, bitmap: Bitmap, rect: Rect) {
        val patch = NinePatch(bitmap, bitmap.ninePatchChunk, null)
        patch.draw(canvas, rect)
    }

    private fun paintTaskPoints(
        context: Context,
        canvas: Canvas,
        points: ArrayList<MapPoint>,
        paint: Paint
    ) {

        if (points.isEmpty()) {
            return
        }

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1F
        paint.color = ResourcesUtils.getColor(context, R.color.focused_line)

        val path = Path()
        for (k in 0 until points.size) {
            val mapPoints = mapCoordinateToCanvas(points[k])
            if (k == 0) {
                path.moveTo(mapPoints.x.toFloat(), mapPoints.y.toFloat())
            } else {
                path.lineTo(mapPoints.x.toFloat(), mapPoints.y.toFloat())
            }
        }
        canvas.drawPath(path, paint)
    }

    fun paintMapWayPoint(
        canvas: Canvas?,
        wayPoint: WayPoint,
        paintText: Paint
    ) {
        //C-充电点
        //P-导航点
        //D-重定位点
        //L-电梯点
        //G-前台点
        //R-房间点
        var type = 0
        if (WayPoint.Type_Charge in wayPoint.type) {
            type = 1
        } else if (WayPoint.Type_Relocation in wayPoint.type) {
            type = 2
        }
        //type:0普通位点，1特殊位点，2定位位点
        val bitmap = when (type) {
            0 -> {
                //普通位点
                if (wayPoint.isFocused) hmBitmaps[TYPE_POINT_NAV_FOCUSED] else hmBitmaps[TYPE_POINT_NAV]
            }
            1 -> {
                //特殊位点
                if (wayPoint.isFocused) hmBitmaps[TYPE_POINT_FUNC_FOCUSED] else hmBitmaps[TYPE_POINT_FUNC]
            }
            else -> {
                //定位位点
                hmBitmaps[TYPE_POINT_RELOC]
            }
        } ?: return

        val xy = drawBitmap(canvas, bitmap, wayPoint, wayPoint.angle)
        drawWayPointText(canvas, wayPoint.name, paintText, bitmap, xy)
    }

    private fun drawWayPointText(
        canvas: Canvas?,
        text: String,
        paintText: Paint,
        bitmapWayPoint: Bitmap,
        xy: FloatArray
    ) {

        if (TextUtils.isEmpty(text)) {
            return
        }

        val TriH = 20
        val BoundsDW = 30
        val TextLR = 20
        val TextTB = 10

        val bitmapWayPointWidth = bitmapWayPoint.width
        val bitmapWayPointHeight = bitmapWayPoint.height
        var position = 0//0上，1下，2左，3右

        val bounds = Rect()
        val textBounds = Rect()
        paintText.getTextBounds(text, 0, text.length, bounds)
        paintText.getTextBounds(text, 0, text.length, textBounds)

        var textX = 0F
        var textY = 0F
        var bitmap: Bitmap?
        var bgX = 0F
        var bgY = 0F

        if (xy[1] - bounds.height() - 10 - 10 - TriH - bounds.height() < 0) {
            position = 1
        }
        if (xy[0] - bounds.width() / 2 - BoundsDW < 0) {
            position = 3
        } else if (xy[0] + bounds.width() / 2 + BoundsDW > canvas?.width?:0) {
            position = 2
        }

        when (position) {
            0 -> {
                //上
                bounds.left -= BoundsDW
                bounds.top -= TextTB
                bounds.right += BoundsDW
                bounds.bottom += TextTB + BoundsDW
                bgX = (bitmapWayPointWidth - bounds.width() / 2).toFloat()
                bgY = (- (bounds.height() / 3)).toFloat()
                bitmap = hmNineBitmaps[TYPE_POINT_TEXT_UP]
                textX = bgX
                textY = bgY + TextTB / 2
            }
            1 -> {
                //下
                bounds.left -= BoundsDW
                bounds.top -= TextTB + TriH
                bounds.right += BoundsDW
                bounds.bottom += TriH
                bgX = (bitmapWayPointWidth - bounds.width() / 2).toFloat()
                bgY = (bounds.height() + bitmapWayPointHeight).toFloat()
                bitmap = hmNineBitmaps[TYPE_POINT_TEXT_DOWN]
                textX = bgX
                textY = bgY + textBounds.height() / 4
            }
            2 -> {
                //左
                bounds.left -= BoundsDW
                bounds.top -= TextLR
                bounds.right += BoundsDW + TriH
                bounds.bottom += TextLR
                bgX = (- bounds.width() * 6 / 7).toFloat()
                bgY = (bounds.height()).toFloat()
                bitmap = hmNineBitmaps[TYPE_POINT_TEXT_LEFT]
                textX = bgX
                textY = bgY
            }
            3 -> {
                //右
                bounds.left -= BoundsDW
                bounds.top -= TextLR
                bounds.right += BoundsDW + BoundsDW
                bounds.bottom += TextLR
                bgX = (bitmapWayPointWidth * 5 / 3).toFloat()
                bgY = (bounds.height()).toFloat()
                bitmap = hmNineBitmaps[TYPE_POINT_TEXT_RIGHT]
                textX = bgX + BoundsDW
                textY = bgY
            }
            else -> {
                return
            }
        }

        canvas?.translate(xy[0] + bgX, xy[1] + bgY)
        drawNineBitmap(canvas, bitmap!!, bounds)
        canvas?.translate(- xy[0] - bgX, - xy[1] - bgY)

        paintText.color = Color.WHITE
        canvas?.drawText(
            text, xy[0] + textX, xy[1] + textY,
            paintText
        )
    }

    /**
     * 测距文字背景
     */
    private fun drawNinepatchPointMessage(
        context: Context,
        canvas: Canvas,
        text: String,
        point: MapPoint,
        paintText: Paint
    ) {

        val bitmap = hmNineBitmaps[TYPE_POINT_MESSAGE] ?: return

        val bounds = Rect()
        paintText.getTextBounds(text, 0, text.length, bounds)
        bounds.left -= 20
        bounds.top -= 20
        bounds.right += 20
        bounds.bottom += 30

        val x = (point.x - bounds.width() / 2 + 20).toFloat()
        val y = (point.y - bounds.height() / 2).toFloat()

        canvas.translate(x, y)
        drawNineBitmap(canvas, bitmap, bounds)
        canvas.translate(-x, -y)

        paintText.color = ResourcesUtils.getColor(context, R.color.msgframe)
        canvas.drawText(
            text, x, y,
            paintText
        )
    }

    /**
     * 测距起点
     */
    private fun drawStartPointText(
        canvas: Canvas,
        point: MapPoint
    ) {

        val image = hmBitmaps[TYPE_POINT_START] ?: return

        val x = (point.x - image.width / 2).toFloat()
        val y = (point.y - image.height - 20).toFloat()

        canvas.drawBitmap(image, x, y, Paint(Paint.ANTI_ALIAS_FLAG))
    }

    fun drawBitmap(canvas: Canvas?, bitmap: Bitmap, mapPoint: MapPoint, angle: Double = 0.0): FloatArray {
        val xyPoint = mapCoordinateToCanvasFloat(mapPoint)
        val matrixPoint = Matrix()
        matrixPoint.postRotate((360 - getMatrixAngle() - angle).toFloat())
        val image = Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width,
            bitmap.height, matrixPoint, true
        )
        val xy = FloatArray(2)
        xy[0] = xyPoint[0] - image.width / 2
        xy[1] = xyPoint[1] - image.height / 2
        canvas?.drawBitmap(
            image,
            xy[0],
            xy[1],
            PaintBitmap
        )
        return xy
    }

    /**
     * 绘制地图圆/椭圆
     */
    private fun paintMapCircle(
        canvas: Canvas?,
        circle: MapRange,
        paint: Paint
    ) {

        if (circle.point_info.isEmpty()) {
            return
        }

        val center = mapCoordinateToCanvas(circle.point_info[0])

        when (circle.point_info.size) {
            2 -> {
                //圆
                val radius = mapCoordinateToCanvas(circle.point_info[1])
                val distance = distance(center, radius).toFloat()
                val path = Path()
                path.addCircle(
                    center.x.toFloat(),
                    center.y.toFloat(),
                    distance,
                    Path.Direction.CW
                )
                canvas?.drawPath(path, paint)
            }
            3 -> {
                //椭圆
                val leftPoint = mapCoordinateToCanvas(circle.point_info[1])
                val bottomPoint = mapCoordinateToCanvas(circle.point_info[2])
                val distanceLeft = distance(center, leftPoint)
                val distanceBottom = distance(center, bottomPoint)
                val left = center.x - distanceLeft
                val top = center.y - distanceBottom
                val right = center.x + distanceLeft
                val bottom = center.y + distanceBottom
                val path = Path()
                path.addOval(
                    left.toFloat(),
                    top.toFloat(),
                    right.toFloat(),
                    bottom.toFloat(),
                    Path.Direction.CW
                )
                canvas?.drawPath(path, paint)
            }
            else -> return
        }
//        if (!TextUtils.isEmpty(circle.name)) {
//            canvas?.drawText(
//                circle.name,
//                (center.x + TextOffsetX).toFloat(),
//                (center.y + TextOffsetY).toFloat(),
//                paintText
//            )
//        }
    }

    private fun paintMapRange(
        context: Context,
        canvas: Canvas,
        range: MapRange,
        paint: Paint
    ) {

//        val mapActivity = context as MapActivity
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = LineWidth
        paint.pathEffect = null

        val colorId = getColor(context, range.range_type, range.work_type)
        paint.color = ResourcesUtils.getColor(context, colorId)
        if (range.isFocused) {
            paint.color = ResourcesUtils.getColor(context, R.color.focused_area)
        }
//        val signKeys = context.resources.getStringArray(R.array.sign_key)

        when (range.graph_type) {
            MapRange.Graph_Polygon -> {
                //多边形/折线
                val isPolygon = range.range_type == MapRange.Range_Area

//                if (Const.isDisplayToolShow(context as BaseActivity)) {
//                    if (isPolygon && range.work_type != MapRange.Work_Forbidden && !mapActivity.isAreaShow(signKeys[MapActivity.DisplayArea])) {
//                        return
//                    }
//
//                    if (range.work_type == MapRange.Work_Forbidden && !mapActivity.isAreaShow(signKeys[MapActivity.DisplayWall])) {
//                        return
//                    }
//
//                    if (!isPolygon && range.work_type != MapRange.Work_Forbidden && !mapActivity.isAreaShow(signKeys[MapActivity.DisplayLine])) {
//                        return
//                    }
//                }

                if (isPolygon/* && range.work_type == MapRange.Work_Forbidden*/) {
                    //内部填充颜色
                    paintMapPolygon(isPolygon, canvas, range, paint/*, paintText*/)
                }

                paint.style = Paint.Style.STROKE
                if (range.work_type == MapRange.Work_Forbidden) {
                    paint.strokeWidth = ForbiddenLineWidth
                    paint.color = ResourcesUtils.getColor(context, R.color.forbidden_line)
                    paint.pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 2f)
                } else {
                    paint.pathEffect = null
                }
                //边线
                paintMapPolygon(isPolygon, canvas, range, paint)
            }
            MapRange.Graph_Circle -> {
                //椭圆/圆
                if (range.work_type != MapRange.Work_Forbidden) {
                    paintMapCircle(canvas, range, paint)
                }

                if (range.work_type == MapRange.Work_Forbidden) {
                    paint.color = ResourcesUtils.getColor(context, R.color.forbidden_frame)
                    paint.strokeWidth = ForbiddenLineWidth
                    paint.style = Paint.Style.STROKE
                    paint.pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 2f)
                    paintMapCircle(canvas, range, paint)
                } else {
                    paint.pathEffect = null
                }
            }
            MapRange.Graph_Bezier -> {
//                if (Const.isDisplayToolShow(context as BaseActivity) && !mapActivity.isAreaShow(signKeys[MapActivity.DisplayLine])) {
//                    return
//                }
                //贝塞尔曲线
                if (range.point_info.size >= 4) {
                    var startIndex = 0
                    while (startIndex + 3 < range.point_info.size) {
                        paintMapBezier(
                            context,
                            canvas,
                            range,
                            startIndex,
                            paint
                        )
                        startIndex += 3
                    }
                }
            }
            MapRange.Graph_Rect -> {
//                if (Const.isDisplayToolShow(context as BaseActivity) && !mapActivity.isAreaShow(signKeys[MapActivity.DisplayArea])) {
//                    return
//                }
                //矩形
                paint.style = Paint.Style.STROKE
                paintMapPolygon(true, canvas, range, paint)
            }
        }

        if (range.isFocused) {
            paintTaskPoints(
                context,
                canvas,
                range.points,
                paint
            )
        }
    }

    private fun drawCircle(
        canvas: Canvas,
        point: MapPoint,
        paint: Paint,
        radius: Float = CircleRadius
    ) {
        paint.style = Paint.Style.FILL
        val dx = point.x.toFloat()
        val dy = point.y.toFloat()
        canvas.drawCircle(dx, dy, radius, paint)
    }

    /**
     * 地图元素中除了使用图片外的元素（机器人、位点、测距）
     */
    fun elementToBitmap(
        context: Context,
        bitmap: Bitmap
    ): Bitmap {

        val newBitmap = bitmap.copy(BitmapConfig, true)
        val canvas = Canvas(newBitmap)

        paintBrushes(canvas, map.brushes)

        //gro_view_tool_top_display显示机器人/虚拟墙/点/线/区域
//        val signKeys = context.resources.getStringArray(R.array.sign_key)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)//消除锯
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = LineWidth
        if (map.ranges.isNotEmpty()) {
            paint.strokeCap = Paint.Cap.ROUND
            if (map.ranges.isNotEmpty()) {
                //先绘制range_type为1的区域
                for (i in 0 until map.ranges.size) {
                    val range = map.ranges[i]

                    if (range.range_type != MapRange.Range_Area) {
                        continue
                    }

                    paintMapRange(
                        context,
                        canvas,
                        range,
                        paint
                    )
                }

                //再绘制range_type为2的路径
                for (i in 0 until map.ranges.size) {
                    val range = map.ranges[i]

                    if (range.range_type != MapRange.Range_Path) {
                        continue
                    }

                    paintMapRange(
                        context,
                        canvas,
                        range,
                        paint
                    )
                }
            }
        }

//        val mapActivity = mContext as MapActivity
//        if (!Const.isDisplayToolShow(mContext as BaseActivity) || mapActivity.isAreaShow(signKeys[MapActivity.DisplayPoint])) {
//            if (map.points.isNotEmpty()) {
//                //位点
//                for (i in 0 until map.points.size) {
//                    val wayPoint = map.points[i]
//                    if (wayPoint.real != WayPoint.Real_Point) {
//                        continue
//                    }
//                    paintMapWayPoint(
//                        scale,
//                        mContext,
//                        canvas,
//                        bitmap,
//                        wayPoint,
//                        map,
//                        paint,
//                        paintText
//                    )
//                }
//            }
//        }

        if (map.polygonPoses.isNotEmpty()) {
            //多边形临时点
            paint.color = ResourcesUtils.getColor(context, R.color.focused_line)
            paint.style = Paint.Style.STROKE
            paint.pathEffect = null
            val path = Path()
            for (k in 0 until map.polygonPoses.size) {
                val mapPoints = mapCoordinateToCanvasFloat(map.polygonPoses[k])
                if (k == 0) {
                    path.moveTo(mapPoints[0], mapPoints[1])
                } else {
                    path.lineTo(mapPoints[0], mapPoints[1])
                }
            }
            canvas.drawPath(path, paint)

            for (k in map.polygonPoses.size - 1 downTo 0) {
                val point = mapCoordinateToCanvas(map.polygonPoses[k])
                drawCircle(canvas, point, paint)
                if (k == 0) {
                    drawStartPointText(canvas, point)
                }
            }
        }

        if (map.recordPoints.isNotEmpty()) {
            //录制路径
            paint.color = ResourcesUtils.getColor(context, R.color.draw_route)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = PathWidth
            paint.pathEffect = null
            val path = Path()
            for (k in 0 until map.recordPoints.size) {
                val mapPoints = mapCoordinateToCanvasFloat(map.recordPoints[k])
                if (k == 0) {
                    path.moveTo(mapPoints[0], mapPoints[1])
                } else {
                    path.lineTo(mapPoints[0], mapPoints[1])
                }
            }
            canvas.drawPath(path, paint)
        }

//        if (map.relocationPos != null) {
//            val wayPoint = WayPoint()
//            wayPoint.x = map.relocationPos!!.x
//            wayPoint.y = map.relocationPos!!.y
//            wayPoint.angle = map.relocationPos!!.angle
//            wayPoint.type.add(WayPoint.Type_Navigation)
//            paintMapWayPoint(scale, mContext, canvas, bitmap, wayPoint, map, paint, paintText)
//        }

        if (map.rulers.isNotEmpty()) {
            //测距尺
            paint.color = Color.YELLOW
            paint.strokeWidth = RulerWidth
            for (i in 1 until map.rulers.size) {
                val pos0 = map.rulers[i - 1]
                val pos1 = map.rulers[i]

                val point0 = mapCoordinateToCanvasFloat(pos0)
                val point1 = mapCoordinateToCanvasFloat(pos1)
                canvas.drawLine(
                    point0[0],
                    point0[1],
                    point1[0],
                    point1[1],
                    paint
                )
            }

            var distance = 0.0
            for (i in 0 until map.rulers.size) {
                val pos = map.rulers[i]
                val point = mapCoordinateToCanvas(pos)
                var text = context.getString(R.string.ruler_start)
                if (i > 0) {
                    val d = distance(pos, map.rulers[i - 1])
                    distance += d
                    val unitId =
                        if (distance > 1000) R.string.ruler_distance_km else R.string.ruler_distance_m
                    var de = distance
                    if (de > 1000) {
                        de /= 1000
                    }
                    text = context.getString(unitId, de)
                }
                paint.color = ResourcesUtils.getColor(context, R.color.msgframe)
                drawCircle(canvas, point, paint)
                paint.color = Color.YELLOW
                drawCircle(canvas, point, paint, CircleRadius - 2)
                if (i == 0) {
                    drawStartPointText(canvas, point)
                } else if (i == map.rulers.size - 1) {
                    drawNinepatchPointMessage(context, canvas, text, point, PaintText)
                }
            }
        }

        return newBitmap
    }

    fun clearFocused() {
        for (i in 0 until map.ranges.size) {
            val range = map.ranges[i]
            range.isFocused = false
        }
        for (i in 0 until map.points.size) {
            val point = map.points[i]
            point.isFocused = false
        }
    }

    /**
     * 屏幕点击坐标转Canvas坐标
     */
    fun pointerToCanvas(point: MapPoint): MapPoint {
        val imageMatrix = imageView.imageMatrix

        // 获取触摸点的坐标 x, y
        val x = point.x.toFloat()
        val y = point.y.toFloat()
        // 目标点的坐标
        val dst = FloatArray(2)
        // 获取到ImageView的matrix
        // 创建一个逆矩阵
        val inverseMatrix = Matrix()
        // 求逆，逆矩阵被赋值
        imageMatrix.invert(inverseMatrix)
        // 通过逆矩阵映射得到目标点 dst 的值
        inverseMatrix.mapPoints(dst, floatArrayOf(x, y))

        val pos = MapPoint()
        pos.x = dst[0].toDouble()
        pos.y = dst[1].toDouble()
        return pos
    }

    /**
     * 屏幕点击坐标转地图坐标
     */
    fun pointerToMap(point: MapPoint): MapPoint? {

        if (imageView.drawable == null) {
            return null
        }

        val posCanvas = pointerToCanvas(point)

        val dstX = posCanvas.x
        val dstY = posCanvas.y
        // 获取图片的大小
        val drawWidth = imageView.drawable.bounds.width()
        val drawHeight = imageView.drawable.bounds.height()
        val deviceX = dstX / drawWidth
        val deviceY = dstY / drawHeight
        // 判断dstX, dstY在Bitmap上的位置即可
        if (!(deviceX < 0 || deviceX > 1 || deviceY < 0 || deviceY > 1)) {
            val pos = MapPoint()
            pos.x =
                map.map_info.min_pos.x + (map.map_info.max_pos.x - map.map_info.min_pos.x) * deviceX
            pos.y =
                map.map_info.max_pos.y - (map.map_info.max_pos.y - map.map_info.min_pos.y) * deviceY
            return pos
        }
        return null
    }

    fun isInDistance(point1: MapPoint, point2: MapPoint, distance: Double = InDistance): Boolean {
        val d = distance(point1, point2)
        return d <= distance
    }

    fun findPointFromPoints(id: Int, points: ArrayList<WayPoint>): WayPoint? {
        if (points.isEmpty()) {
            return null
        }
        for (i in 0 until points.size) {
            val point = points[i]
            if (point.point_id == id) {
                return point
            }
        }
        return null
    }

    fun findFocusedElement(pointF: PointF, findWayPoint: Boolean = false): Boolean {

        clearFocused()

        val pos = MapPoint()
        pos.x = pointF.x.toDouble()
        pos.y = pointF.y.toDouble()

        val fingerPoint = pointerToMap(pos) ?: return false

        var isFound = false

//        val signKeys = resources.getStringArray(R.array.sign_key)

        for (i in 0 until map.points.size) {
            val point = map.points[i]
//            if (Const.isDisplayToolShow(this) && !isAreaShow(signKeys[DisplayPoint])) {
//                //不显示时不做点击检测
//                point.isFocused = false
//                continue
//            }
            if (isInDistance(fingerPoint, point)) {
                isFound = true
                point.isFocused = true
            } else {
                point.isFocused = false
            }
        }

        if (!findWayPoint) {
            for (i in 0 until map.ranges.size) {
                val range = map.ranges[i]
                var isShow = true
//                if (Const.isDisplayToolShow(this)) {
//                    //不显示时不做点击检测
//                    when (range.graph_type) {
//                        MapRange.Graph_Polygon -> {
//                            //多边形/折线
//                            val isPolygon = range.range_type == MapRange.Range_Area
//
//                            if (isPolygon && range.work_type != MapRange.Work_Forbidden && !isAreaShow(signKeys[DisplayArea])) {
//                                isShow = false
//                            }
//
//                            if (range.work_type == MapRange.Work_Forbidden && !isAreaShow(signKeys[DisplayWall])) {
//                                isShow = false
//                            }
//
//                            if (!isPolygon && range.work_type != MapRange.Work_Forbidden && !isAreaShow(signKeys[DisplayLine])) {
//                                isShow = false
//                            }
//                        }
//                        MapRange.Graph_Bezier -> {
//                            if (!isAreaShow(signKeys[DisplayLine])) {
//                                isShow = false
//                            }
//                        }
//                        MapRange.Graph_Rect -> {
//                            if (!isAreaShow(signKeys[DisplayArea])) {
//                                isShow = false
//                            }
//                        }
//                    }
//                }
                if (isShow) {
                    for (j in 0 until range.point_id.size) {
                        val pointID = range.point_id[j]
                        val point = findPointFromPoints(pointID, map.points) ?: continue
                        if (point.isFocused) {
                            range.isFocused = true
                            break
                        }
                    }
                }
                //TODO 未保存地图，新建的区域点击移动点
//                var isFocused = false
//                for (j in 0 until range.point_info.size) {
//                    val point = range.point_info[j]
//                    if (MapUtilsB.isInDistance(fingerPoint, point)) {
//                        isFocused = true
//                    }
//                }
//                range.isFocused = isFocused
//                if (isFocused && !isFound) {
//                    isFound = true
//                }
            }
        }

        return isFound
    }

    fun findFocusedPos(pointF: PointF): WayPoint? {
        val pos = MapPoint()
        pos.x = pointF.x.toDouble()
        pos.y = pointF.y.toDouble()
        val fingerPoint = pointerToMap(pos) ?: return null

        var distance = Double.MAX_VALUE
        var result: WayPoint? = null
        for (i in 0 until map.points.size) {
            val point = map.points[i]
            if (point.isFocused) {
                point.distanceClick = distance(point, fingerPoint)
                if (point.distanceClick < distance) {
                    distance = point.distanceClick
                    result = point
                }
            }
        }
        return result

        //TODO 未保存地图，新建的区域点击移动点
//        if (pointF == null) {
//            return null
//        }
//        val fingerPoint = MapUtilsB.pointerToMap(pointF, tivMap, Const.map!!) ?: return null
//        for (i in 0 until map.ranges.size) {
//            val range = map.ranges[i]
//            if (range.isFocused) {
//                for (j in 0 until range.point_info.size) {
//                    val point = range.point_info[j]
//                    if (MapUtilsB.isInDistance(fingerPoint, point)) {
//                        val wp = WayPoint()
//                        wp.x = point.x
//                        wp.y = point.y
//                        return wp
//                    }
//                }
//            }
//        }

        return null
    }

    /**
     * 位点移动后同步range里的位点数据
     */
    fun onChangeOver(wayPoint: WayPoint): Boolean {
        wayPoint.isChanged = true
        for (i in 0 until map.ranges.size) {
            val range = map.ranges[i]
            if (range.range_id == 0) {
                continue
            }
            for (j in 0 until range.point_info.size) {
                val rangePoint = range.point_info[j]
                if (rangePoint.point_id == wayPoint.point_id) {
                    if (rangePoint.x != wayPoint.x || rangePoint.y != wayPoint.y || rangePoint.angle != wayPoint.angle) {
                        rangePoint.type =
                            if (rangePoint.point_id == 0) RangePoint.TYPE_NEW else RangePoint.TYPE_MODIFY
                        if (rangePoint.type == RangePoint.TYPE_MODIFY) {
                            range.isChanged = true
                        }
                    }
                    rangePoint.x = wayPoint.x
                    rangePoint.y = wayPoint.y
                    rangePoint.angle = wayPoint.angle
                }
            }
        }
        return true
    }
}