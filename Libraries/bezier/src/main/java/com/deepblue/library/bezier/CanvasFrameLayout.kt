package com.deepblue.library.bezier

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.deepblue.library.bezier.utils.ViewUtils

/**
 * 本模块中的所有自定义View必须都用本类作为Parent
 */
class CanvasFrameLayout: FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setOnTouchListener { v, event -> ViewUtils.resort(this, event) }
    }
}