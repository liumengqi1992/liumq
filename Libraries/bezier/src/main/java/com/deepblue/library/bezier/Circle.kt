package com.deepblue.library.bezier

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import com.deepblue.library.bezier.utils.BitmapUtils
import com.deepblue.library.bezier.utils.PointUtils
import org.jetbrains.anko.doAsync

/**
 * 圆
 */
class Circle(context: Context) : AbstractView(context) {

    private val bitmaps = ArrayList<Bitmap?>()

    override fun onDestroy() {
        super.onDestroy()
        for (i in 0 until bitmaps.size) {
            bitmaps[i] = null
        }
        bitmaps.clear()
    }

    /**
     * 初始化起点
     */
    override fun init(clickListener: ClickListener/*, center: Point, radius: Int = 200*/) {
        super.init(clickListener)
//        points.add(center)
//
//        val screenWidth = context.resources.displayMetrics.widthPixels
//
//        var x = center.x + radius
//        var y = center.y
//        if (x >= screenWidth) {
//            x = center.x - radius
//            if (x <= 0) {
//                x = center.x
//                y = center.y - radius
//                if (y < 0) {
//                    y = center.y + radius
//                }
//            }
//        }
//        val point = Point(x, y)
//        points.add(point)

//        addStep()

        doAsync {
            val bmMove = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_move)
            val bmMax = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_maximize)
            bitmaps.add(bmMove)
            bitmaps.add(bmMax)
        }
    }

    override fun clickPoint(point: Point) {

    }

    override fun addPoint(point: Point) {

        if (points.isNotEmpty()) {
            return
        }

        val radius = 200

        points.add(Point(point.x, point.y))

        val screenWidth = context.resources.displayMetrics.widthPixels

        var x = point.x + radius
        var y = point.y
        if (x >= screenWidth) {
            x = point.x - radius
            if (x <= 0) {
                x = point.x
                y = point.y - radius
                if (y < 0) {
                    y = point.y + radius
                }
            }
        }

        points.add(Point(x, y))
        invalidate()
        addStep()
    }

    override fun onDraw(canvas: Canvas) {

        if (points.isEmpty()) {
            return
        }

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = PointUtils.PaintStrokeWidth
        paint.color = PointUtils.PaintColor
        val radius = PointUtils.distance(points[0], points[1]).toFloat()
        canvas.drawCircle(points[0].x.toFloat(), points[0].y.toFloat(), radius, paint)

        paint.style = Paint.Style.FILL
        paint.strokeWidth = PointUtils.PointStrokeWidth
        paint.color = PointUtils.PointColor
        for (i in 0 until points.size) {
            //画端点
            val point = points[i]
            canvas.drawCircle(point.x.toFloat(), point.y.toFloat(), PointUtils.PointRadius, paint)

            if (bitmaps.isNotEmpty() && bitmaps[i] != null) {
                canvas.drawBitmap(
                    bitmaps[i]!!,
                    point.x.toFloat() - bitmaps[i]!!.width / 2,
                    point.y.toFloat() - bitmaps[i]!!.height / 2,
                    paint
                )
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (super.onTouchEvent(event)) {
                    return true
                }
                if (points.isEmpty()) {
                    downEmpty(event)
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (currentPointIndex >= 0 && currentPointIndex < points.size) {

                    val dx = event.x.toInt() - points[currentPointIndex].x
                    val dy = event.y.toInt() - points[currentPointIndex].y

                    points[currentPointIndex].x = event.x.toInt()
                    points[currentPointIndex].y = event.y.toInt()

                    if (currentPointIndex == 0) {
                        //移动圆心
                        points[1].x += dx
                        points[1].y += dy
                    }
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