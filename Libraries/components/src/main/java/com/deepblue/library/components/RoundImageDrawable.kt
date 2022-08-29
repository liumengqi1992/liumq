package com.deepblue.library.components

import android.graphics.*
import android.graphics.Shader.TileMode
import android.graphics.drawable.Drawable

/**
 * 圆角Drawable
 */
class RoundImageDrawable(private val mBitmap: Bitmap) : Drawable() {

    private val mPaint = Paint()

    private var rectF: RectF? = null

    init {
        val bitmapShader = BitmapShader(
            mBitmap, TileMode.CLAMP,
            TileMode.CLAMP
        )

        mPaint.isAntiAlias = true
        mPaint.shader = bitmapShader
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        rectF = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(rectF!!, 60f, 60f, mPaint)
    }

    override fun getIntrinsicWidth(): Int {
//        return mBitmap.width
        return Math.min(mBitmap.width, mBitmap.height)
    }

    override fun getIntrinsicHeight(): Int {
//        return mBitmap.height
        return Math.min(mBitmap.width, mBitmap.height)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        mPaint.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

}
