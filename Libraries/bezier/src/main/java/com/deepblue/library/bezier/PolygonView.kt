package com.deepblue.library.bezier

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import com.deepblue.library.bezier.utils.BitmapUtils
import com.deepblue.library.bezier.utils.PointUtils
import org.jetbrains.anko.doAsync

/**
 * 多边形
 */
class PolygonView(context: Context) : AbstractView(context) {

    companion object {
        private const val MAX_SIZE = 8
    }
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
//     * 初始化起点
//     */
//    fun init(clickListener: ClickListener/*, start: Point*/) {
//        this.clickListener = clickListener
//        points.clear()
////        points.add(start)
//        editable = true
//        addStep()
//    }

    override fun addPoint(point: Point) {
        if (!editable || closed) {
            return
        }
        if (points.size < MAX_SIZE) {
            currentPointIndex = points.size
            points.add(Point(point.x, point.y))
            invalidate()
            addStep()
        }
    }

    override fun clickPoint(point: Point) {
        if (!editable || points.isEmpty() || closed) {
            return
        }
        if (point.x == points[0].x && point.y == points[0].y) {
            //点击到起点
            closed = true
            invalidate()
            addStep()
        }
    }

    override fun onDraw(canvas: Canvas) {

//        paint.style = Paint.Style.FILL
        paint.style = Paint.Style.STROKE
        paint.color = PointUtils.PaintColor
        paint.strokeWidth = PointUtils.PaintStrokeWidth
        val path = Path()
        for (i in 0 until points.size) {
            val point = points[i]
            //绘制连线
//            val next = when {
//                i < points.size - 1 -> points[i + 1]
//                closed -> points[0]
//                else -> null
//            } ?: continue
//            canvas.drawLine(point.x.toFloat(), point.y.toFloat(), next.x.toFloat(), next.y.toFloat(), paint)
            if (i == 0) {
                path.moveTo(point.x.toFloat(), point.y.toFloat())
            } else {
                path.lineTo(point.x.toFloat(), point.y.toFloat())
                if (closed && i == points.size - 1) {
                    path.lineTo(points[0].x.toFloat(), points[0].y.toFloat())
                }
            }
        }
        canvas.drawPath(path, paint)

        paint.color = PointUtils.PointColor
        paint.strokeWidth = PointUtils.PointStrokeWidth
        for (i in 0 until points.size) {
            //画端点
            val point = points[i]
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
                if (editable && points.size < MAX_SIZE) {
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