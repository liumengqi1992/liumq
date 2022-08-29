package com.deepblue.library.mapbezier.utils

import android.view.MotionEvent
import com.deepblue.library.mapbezier.AbstractView

object ViewUtils {

    fun setEditable(views: ArrayList<AbstractView>, editable: Boolean = false) {
        for (i in 0 until views.size) {
            views[i].editable = editable
        }
    }
}