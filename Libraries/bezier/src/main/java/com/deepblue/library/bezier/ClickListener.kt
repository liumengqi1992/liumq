package com.deepblue.library.bezier

import android.graphics.Point

interface ClickListener {

    fun onClick(point: Point)

    fun onLongClick(point: Point)

    fun onTouched(point: Point)

    fun onClickEmpty(point: Point)

    fun onLongClickEmpty(point: Point)
}