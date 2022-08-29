package com.deepblue.library.bezier

import android.content.Context
import android.view.MotionEvent
import com.deepblue.library.bezier.utils.BitmapUtils
import com.deepblue.library.bezier.utils.PointUtils
import org.jetbrains.anko.doAsync
import android.graphics.*
import android.graphics.Bitmap

/**
 * 有方向的点
 */
class SinglePoint(context: Context) : AbstractView(context) {

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
    fun init(clickListener: ClickListener, center: Point) {
        this.clickListener = clickListener
        points.clear()
        points.add(center)

        addStep()

        doAsync {
            bitmap = BitmapUtils.getBitmapFromVectorDrawable(context, R.drawable.ic_arrow)

            offsetX = bitmap!!.width / 2F
            offsetY = bitmap!!.height / 2F
        }
    }


    override fun addPoint(point: Point) {
        //TODO
    }

    override fun clickPoint(point: Point) {
        //TODO
    }

    /**
     * 点绘制的位置与实际有偏差值，因此需用该函数获取绘制坐标
     */
    fun getPoint(): Point {
        return Point(points[0].x + offsetX.toInt(), points[0].y + offsetY.toInt())
    }

    override fun onDraw(canvas: Canvas) {

        if (bitmap == null) {
            return
        }

        paint.style = Paint.Style.FILL
        paint.strokeWidth = PointUtils.PointStrokeWidth
        paint.color = PointUtils.PointColor
        val point = points[0]

        val dx = point.x + offsetX
        val dy = point.y + offsetY
        canvas.drawCircle(dx, dy, PointUtils.PointRadius, paint)

        mMatrix.reset()
        mMatrix.postTranslate(-offsetX, -offsetY)
        mMatrix.postRotate(360F - degree)
        mMatrix.postTranslate(point.x + offsetX, point.y + offsetY)
        canvas.drawBitmap(bitmap!!, mMatrix, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                return super.onTouchEvent(event)
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