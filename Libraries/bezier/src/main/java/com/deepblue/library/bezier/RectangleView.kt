package com.deepblue.library.bezier

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.view.MotionEvent
import com.deepblue.library.bezier.utils.BitmapUtils
import com.deepblue.library.bezier.utils.PointUtils
import org.jetbrains.anko.doAsync

/**
 * 矩形
 */
open class RectangleView(context: Context) : AbstractView(context) {

    protected val bitmaps = ArrayList<Bitmap?>()

    override fun onDestroy() {
        super.onDestroy()
        for (i in 0 until bitmaps.size) {
            bitmaps[i] = null
        }
        bitmaps.clear()
    }

    /**
     * 初始化
     * @param topleft 左上角
     * @param rightbottom 右下角
     */
    fun init(clickListener: ClickListener, topleft: Point, rightbottom: Point) {
        this.clickListener = clickListener
        points.clear()
        points.add(topleft)
        points.add(rightbottom)

        doAsync {
            val bmMove = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_move)
            val bmMax = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_maximize)
            bitmaps.add(bmMove)
            bitmaps.add(bmMax)
        }

        addStep()
    }

    override fun addPoint(point: Point) {
        //TODO
    }

    override fun clickPoint(point: Point) {
        //TODO
    }

    /**
     * 绘图时只用到两个点，但提交后台需要四个点
     */
    fun getPoint(): Array<Point> {
        val left = Math.min(points[0].x, points[1].x)
        val top = Math.min(points[0].y, points[1].y)
        val right = Math.max(points[0].x, points[1].x)
        val bottom = Math.max(points[0].y, points[1].y)
        return arrayOf(Point(left, top), Point(right, top), Point(right, bottom), Point(left, bottom))
    }

    override fun onDraw(canvas: Canvas) {

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = PointUtils.PaintStrokeWidth
        paint.color = PointUtils.PaintColor
        val left = Math.min(points[0].x, points[1].x).toFloat()
        val top = Math.min(points[0].y, points[1].y).toFloat()
        val right = Math.max(points[0].x, points[1].x).toFloat()
        val bottom = Math.max(points[0].y, points[1].y).toFloat()
        canvas.drawRect(left, top, right, bottom, paint)

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
                return super.onTouchEvent(event)
            }
            MotionEvent.ACTION_MOVE -> {
                if (currentPointIndex >= 0 && currentPointIndex < points.size) {

                    val dx = event.x.toInt() - points[currentPointIndex].x
                    val dy = event.y.toInt() - points[currentPointIndex].y

                    points[currentPointIndex].x = event.x.toInt()
                    points[currentPointIndex].y = event.y.toInt()

                    if (currentPointIndex == 0) {
                        //移动矩形
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