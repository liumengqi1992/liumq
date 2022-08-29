package com.deepblue.library.components

import android.content.Context
import android.util.AttributeSet
import android.view.View

class SquareImageView: android.support.v7.widget.AppCompatImageView {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val min = Math.min(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(View.getDefaultSize(0, widthMeasureSpec), View.getDefaultSize(0, heightMeasureSpec))

        val childWidthSize = Math.min(widthMeasureSpec, heightMeasureSpec)
        //高度和宽度一样
        val width = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY)
        super.onMeasure(width, width)
    }
}