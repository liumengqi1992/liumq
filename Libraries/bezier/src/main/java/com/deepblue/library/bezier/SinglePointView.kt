package com.deepblue.library.bezier

import android.content.Context
import android.view.MotionEvent
import com.deepblue.library.bezier.utils.BitmapUtils
import com.deepblue.library.bezier.utils.PointUtils
import org.jetbrains.anko.doAsync
import android.graphics.*
import android.graphics.Bitmap
import android.graphics.Point

/**
 * 有方向的点
 */
class SinglePointView(context: Context) : AbstractView(context) {

    private var bitmap: Bitmap? = null
    private val mMatrix = Matrix()
    private var offsetX = 0F
    private var offsetY = 0F
    var degree = 0
        set(value) {
            field = value
            invalidate()
        }

    override fun onDestroy() {
        super.onDestroy()
        bitmap = null
    }

    /**
     * 初始化起点
     */
    override fun init(clickListener: ClickListener/*, center: Point*/) {
        super.init(clickListener)
//        this.clickListener = clickListener
//        points.clear()
//        points.add(center)

//        addStep()

        doAsync {
            bitmap = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_arrow)

            offsetX = bitmap!!.width / 2F
            offsetY = bitmap!!.height / 2F
        }
    }


    override fun addPoint(point: Point) {
        points.add(Point(point.x, point.y))
        invalidate()
        addStep()
    }

    override fun clickPoint(point: Point) {
        //TODO
    }

    /**
     * 点绘制的位置与实际有偏差值，因此需用该函数获取绘制坐标
     */
    fun getPoint(index: Int): Point {
        return Point(points[index].x + offsetX.toInt(), points[index].y + offsetY.toInt())
    }

    override fun onDraw(canvas: Canvas) {

        if (bitmap == null || points.isEmpty()) {
            return
        }

        paint.style = Paint.Style.FILL
        paint.strokeWidth = PointUtils.PointStrokeWidth
        paint.color = PointUtils.PointColor

        for (i in 0 until points.size) {
            val point = points[i]

            val dx = point.x + offsetX
            val dy = point.y + offsetY
            canvas.drawCircle(dx, dy, PointUtils.PointRadius, paint)

            mMatrix.reset()
            mMatrix.postTranslate(-offsetX, -offsetY)
            mMatrix.postRotate(360F - degree)
            mMatrix.postTranslate(point.x + offsetX, point.y + offsetY)
            canvas.drawBitmap(bitmap!!, mMatrix, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
//                return super.onTouchEvent(event)
                if (super.onTouchEvent(event)) {
                    return true
                }
                if (editable) {
                    downEmpty(event)
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (currentPointIndex == 0) {

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