package com.deepblue.library.bezier

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import com.deepblue.library.bezier.utils.BitmapUtils
import com.deepblue.library.bezier.utils.PointUtils
import org.jetbrains.anko.doAsync

/**
 * 贝赛尔曲线（一阶、二阶、三阶）
 */
class BazierView(context: Context) : AbstractView(context) {

    private var bitmap: Bitmap? = null

    init {
        doAsync {
            bitmap = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_move)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bitmap = null
    }

//    /**
//     * 初始化每个端点
//     * @param start 起点坐标
//     * @param end 终点坐标
//     * @param numPoints 中间端点个数，0一阶（线段），1二阶，2三阶……
//     */
//    override fun init(clickListener: ClickListener/*, start: Point, end: Point, numPoints: Int = 0*/) {
//        super.init(clickListener)
////        points.add(start)
////        if (numPoints > 0) {
////            val perX = (end.x - start.x) / (numPoints + 1)
////            val perY = (end.y - start.y) / (numPoints + 1)
////            for (i in 0 until numPoints) {
////                val point = Point(start.x + perX * (i + 1), start.y + perY * (i + 1))
////                points.add(point)
////            }
////        }
////        points.add(end)
//
//        addStep()
//    }

    override fun addPoint(point: Point) {

        val end = Point(point.x, point.y)
        if (points.size > 0) {
            //添加控制点
            val numPoints = 2
            val start = points[points.size - 1]
            val perX = (end.x - start.x) / (numPoints + 1)
            val perY = (end.y - start.y) / (numPoints + 1)
            for (i in 0 until numPoints) {
                points.add(Point(start.x + perX * (i + 1), start.y + perY * (i + 1)))
            }
        }

        points.add(end)
        invalidate()
        addStep()
    }

    override fun clickPoint(point: Point) {
        //TODO
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {

//        if (points.size > 2) {
//            //画贝塞尔曲线
//            paint.strokeWidth = PointUtils.PaintStrokeWidth
//            paint.color = PointUtils.PaintColor
//            paint.style = Paint.Style.STROKE
//            val path = Path()
//            path.moveTo(points[0].x.toFloat(), points[0].y.toFloat())
//            when (points.size) {
//                3 -> {
//                    //二阶
//                    path.quadTo(
//                        points[1].x.toFloat(),
//                        points[1].y.toFloat(),
//                        points[2].x.toFloat(),
//                        points[2].y.toFloat()
//                    )
//                }
//                4 -> {
//                    //三阶
//                    path.cubicTo(
//                        points[1].x.toFloat(),
//                        points[1].y.toFloat(),
//                        points[2].x.toFloat(),
//                        points[2].y.toFloat(),
//                        points[3].x.toFloat(),
//                        points[3].y.toFloat()
//                    )
//                }
//            }
//            canvas.drawPath(path, paint)
//        }

        if (points.size >= 4) {
            paint.strokeWidth = PointUtils.PaintStrokeWidth
            paint.color = PointUtils.PaintColor
            paint.style = Paint.Style.STROKE
            var i = 0
            while (i < points.size - 3) {
                val path = Path()
                path.moveTo(points[i].x.toFloat(), points[i].y.toFloat())
                path.cubicTo(
                    points[i + 1].x.toFloat(),
                    points[i + 1].y.toFloat(),
                    points[i + 2].x.toFloat(),
                    points[i + 2].y.toFloat(),
                    points[i + 3].x.toFloat(),
                    points[i + 3].y.toFloat()
                )
                canvas.drawPath(path, paint)
                i += 3
            }
        }

        for (i in 0 until points.size) {
            val point = points[i]
            //绘制连线

            paint.style = Paint.Style.STROKE
            if (i < points.size - 1) {
//                if (points.size == 2) {
//                    //仅有两个点，即为线段
//                    paint.strokeWidth = PointUtils.PaintStrokeWidth
//                    paint.color = PointUtils.PaintColor
//                } else {
//                    paint.strokeWidth = PointUtils.LineStrokeWidth
//                    paint.color = PointUtils.PointColor
//                }
                paint.strokeWidth = PointUtils.LineStrokeWidth
                paint.color = PointUtils.PointColor
                val next = points[i + 1]
                canvas.drawLine(point.x.toFloat(), point.y.toFloat(), next.x.toFloat(), next.y.toFloat(), paint)
            }

            //画端点
            paint.color = PointUtils.PointColor
            paint.style = Paint.Style.FILL
            paint.strokeWidth = PointUtils.PointStrokeWidth
            canvas.drawCircle(point.x.toFloat(), point.y.toFloat(), PointUtils.PointRadius, paint)

            if (bitmap != null) {
                canvas.drawBitmap(
                    bitmap!!,
                    point.x.toFloat() - bitmap!!.width / 2,
                    point.y.toFloat() - bitmap!!.height / 2,
                    paint
                )
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (super.onTouchEvent(event)) {
                    editable = true
                    return true
                }
                if (editable) {
                    downEmpty(event)
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (currentPointIndex >= 0 && currentPointIndex < points.size) {
                    points[currentPointIndex].x = event.x.toInt()
                    points[currentPointIndex].y = event.y.toInt()
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (currentPointIndex >= 0) {
                    addStep()
                }
                currentPointIndex = -1
            }
        }
        return super.onTouchEvent(event)
    }
}