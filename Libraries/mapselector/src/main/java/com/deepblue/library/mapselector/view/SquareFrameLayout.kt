package com.deepblue.library.mapselector.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * 正方形布局
 * Created by CaoJun on 2017-12-20.
 */
class SquareFrameLayout: FrameLayout {
    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val min = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(min, min)
    }
}