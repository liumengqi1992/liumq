package com.deepblue.font

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextClock
import android.widget.TextView

/**
 * 自定义字体 TextClock
 */
@SuppressLint("AppCompatCustomView")
class FontTextClock : TextClock {
    constructor(context: Context) : super(context) {
        initTypeFace(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initTypeFace(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initTypeFace(context)
    }

    private fun initTypeFace(context: Context) {
        if (TextUtils.isEmpty(Font.textFont)) {
            return
        }
        val typeface = Typeface.createFromAsset(context.assets, Font.textFont)
        setTypeface(typeface)
    }
}
