package com.deepblue.library.components

import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.Shader.TileMode

/**
 * 圆形Drawable
 */
class CircleImageDrawable(bitmap: Bitmap) : Drawable() {

    private val mPaint = Paint()
    private val mWidth: Int
    private val mBitmap: Bitmap = bitmap

    init {
        val bitmapShader = BitmapShader(
            bitmap, TileMode.CLAMP,
            TileMode.CLAMP
        )
        mPaint.isAntiAlias = true
        mPaint.shader = bitmapShader
        mWidth = Math.min(mBitmap.width, mBitmap.height)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(mWidth / 2F, mWidth / 2F, mWidth / 2F, mPaint)
    }

    override fun getIntrinsicWidth(): Int {
        return mWidth
    }

    override fun getIntrinsicHeight(): Int {
        return mWidth
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