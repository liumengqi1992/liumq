package com.deepblue.library.bezier

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.os.Build
import com.deepblue.library.bezier.utils.PointUtils

/**
 * 椭圆，仅Android5.0及以上支持
 */
class Oval(context: Context) : Rectangle(context) {

    override fun onDraw(canvas: Canvas) {

        val left = Math.min(points[0].x, points[1].x).toFloat()
        val top = Math.min(points[0].y, points[1].y).toFloat()
        val right = Math.max(points[0].x, points[1].x).toFloat()
        val bottom = Math.max(points[0].y, points[1].y).toFloat()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = PointUtils.PaintStrokeWidth
            paint.color = PointUtils.PaintColor
            canvas.drawOval(left, top, right, bottom, paint)
        }

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = PointUtils.PointStrokeWidth
        paint.color = PointUtils.PointColor
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
}