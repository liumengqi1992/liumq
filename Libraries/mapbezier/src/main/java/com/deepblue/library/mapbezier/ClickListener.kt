package com.deepblue.library.mapbezier

import android.graphics.PointF

interface ClickListener {

    fun onClick(pointF: PointF)

    fun onLongClick(pointF: PointF)

    fun onTouched(pointF: PointF)
}