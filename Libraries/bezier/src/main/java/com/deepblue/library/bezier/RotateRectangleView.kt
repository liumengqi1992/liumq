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
 * 可旋转矩形
 */
class RotateRectangleView(context: Context) : AbstractView(context) {

    companion object {
        private const val TopLeft = 0
        private const val TopRight = 1
        private const val BottomRight = 2
        private const val BottomLeft = 3
    }

    //各端点和左上角的夹角
    private val radians = arrayOf(0.0, 0.0, 0.0, 0.0)
    private var widthRect = 0
    private var heightRect = 0

    private val bitmaps = ArrayList<Bitmap?>()

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
     * @param width 宽
     * @param height 高
     */
    fun init(clickListener: ClickListener, topleft: Point, width: Int, height: Int) {
        this.clickListener = clickListener
        points.clear()
        points.add(topleft)

        val topright = Point(topleft.x + width, topleft.y)
        points.add(topright)

        val bottomright = Point(topright.x, topleft.y + height)
        points.add(bottomright)

        val bttomleft = Point(topleft.x, topleft.y + height)
        points.add(bttomleft)

        addStep()

        widthRect = width
        heightRect = height

        doAsync {

            val bmMove = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_move)
            val bmMax = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_maximize)
            val bmRotate = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_repeat)
            bitmaps.add(bmMove)
            bitmaps.add(bmRotate)
            bitmaps.add(bmMax)
            bitmaps.add(bmRotate)
        }
    }

    override fun addPoint(point: Point) {
        //TODO
    }

    override fun clickPoint(point: Point) {
        //TODO
    }

    override fun onDraw(canvas: Canvas) {

        paint.style = Paint.Style.FILL
        paint.strokeWidth = PointUtils.PaintStrokeWidth
        paint.color = PointUtils.PaintColor
        paint.strokeWidth = PointUtils.PointStrokeWidth
        for (i in 0 until points.size) {
            val point = points[i]
            //绘制连线
            val next = if (i < points.size - 1) {
                points[i + 1]
            } else {
                points[0]
            }
            canvas.drawLine(point.x.toFloat(), point.y.toFloat(), next.x.toFloat(), next.y.toFloat(), paint)
        }

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
//                currentPointIndex = -1
//                for (i in 0 until points.size) {
//                    if (PointUtils.isInCircle(event.x, event.y, points[i])) {
//                        currentPointIndex = i
//
//                        if (i == TopRight || i == BottomLeft) {
//                            calRadians(i, points[i])
//                        }
//                        return super.onTouchEvent(event)
//                    }
//                }
                if (super.onTouchEvent(event)) {
                    if (currentPointIndex == TopRight || currentPointIndex == BottomLeft) {
                        calRadians(currentPointIndex, points[currentPointIndex])
                    }
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (currentPointIndex >= 0 && currentPointIndex < points.size) {

                    val dx = event.x.toInt() - points[currentPointIndex].x
                    val dy = event.y.toInt() - points[currentPointIndex].y

                    points[currentPointIndex].x = event.x.toInt()
                    points[currentPointIndex].y = event.y.toInt()

                    when (currentPointIndex) {
                        TopLeft -> {
                            //左上角，整体移动
                            for (i in 1 until points.size) {
                                points[i].x += dx
                                points[i].y += dy
                            }
                        }
                        TopRight,BottomLeft -> {
                            //右上、左下角，旋转
                            val angle = PointUtils.getDegree(points[TopLeft].x.toFloat(), points[TopLeft].y.toFloat(), event.x, event.y)
                            val radian = PointUtils.getRadian(angle)
                            radians[currentPointIndex] = radian
                            if (currentPointIndex == TopRight) {
                                //右上
                                calTopRightXY()
                            } else {
                                //左下
                                calBottomLeftXY()
                            }

                            calPoints(currentPointIndex, points[currentPointIndex])
                        }
                        BottomRight -> {
                            //右下角，改变宽高
                            calPoints(points[currentPointIndex])
                        }
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

    /**
     * 根据右上角或左下角，计算其他各个端点坐标
     */
    private fun calPoints(index: Int, point: Point) {
        calRadians(index, point)

        //右下
        calBottomRightXY()

        when (index) {
            TopRight -> {
                //右上
                calBottomLeftXY()
            }
            BottomLeft -> {
                //左下
                calTopRightXY()
            }
        }
    }

    private fun calBottomRightXY() {
        //对角线
        val diagonal = Math.sqrt(Math.pow(widthRect.toDouble(), 2.0) + Math.pow(heightRect.toDouble(), 2.0))
        points[BottomRight].x = (diagonal * Math.cos(radians[BottomRight]) + points[TopLeft].x).toInt()
        points[BottomRight].y = (points[TopLeft].y - diagonal * Math.sin(radians[BottomRight])).toInt()
    }

    private fun calBottomLeftXY() {
        points[BottomLeft].x = (heightRect * Math.cos(radians[BottomLeft]) + points[TopLeft].x).toInt()
        points[BottomLeft].y = (points[TopLeft].y - heightRect * Math.sin(radians[BottomLeft])).toInt()
    }

    private fun calTopRightXY() {
        points[TopRight].x = (widthRect * Math.cos(radians[TopRight]) + points[TopLeft].x).toInt()
        points[TopRight].y = (points[TopLeft].y - widthRect * Math.sin(radians[TopRight])).toInt()
    }

    /**
     * 通过右上角和左下角的坐标，计算各个端点弧度
     */
    private fun calRadians(index: Int, point: Point) {
        val angle = PointUtils.getDegree(points[TopLeft].x.toFloat(), points[TopLeft].y.toFloat(), point.x.toFloat(), point.y.toFloat())
        val radian = PointUtils.getRadian(angle)
        radians[index] = radian
        when (index) {
            TopRight -> {
                //右上
                radians[BottomLeft] = PointUtils.getRadian(270.0) + radian
            }
            BottomLeft -> {
                //左下
                radians[TopRight] = radian - PointUtils.getRadian(270.0)
            }
        }
        val arctg = Math.atan(heightRect.toDouble() / widthRect)
        radians[BottomRight] = PointUtils.getRadian(360.0) - arctg + radians[TopRight]
    }

    /**
     * 通过对角（右下角）计算其他各个端点弧度
     */
    private fun calPoints(point: Point) {
        calRadians(point)

        calBottomRightXY()
        calBottomLeftXY()
        calTopRightXY()
    }

    /**
     * 通过对角（右下角）计算各个端点弧度
     */
    private fun calRadians(point: Point) {
        val angle = PointUtils.getDegree(points[TopLeft].x.toFloat(), points[TopLeft].y.toFloat(), point.x.toFloat(), point.y.toFloat())
        val radian = PointUtils.getRadian(angle)
        radians[BottomRight] = radian

        val diagonal = PointUtils.distance(point, points[TopLeft])
        val height = diagonal * Math.abs(Math.sin(radian))
        val width = diagonal * Math.abs(Math.cos(radian))
        heightRect = height.toInt()
        widthRect = width.toInt()

        radians[TopRight] = angle - 360 + PointUtils.getDegree(Math.atan(height / width))
        radians[BottomLeft] = radians[TopRight] + 270

        radians[TopRight] = PointUtils.getRadian(radians[TopRight])
        radians[BottomLeft] = PointUtils.getRadian(radians[BottomLeft])
    }
}