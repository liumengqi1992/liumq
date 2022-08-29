package com.deepblue.library.bezier.utils

import android.view.MotionEvent
import android.view.ViewGroup
import com.deepblue.library.bezier.AbstractView
import com.deepblue.library.bezier.PolygonView

object ViewUtils {

    fun resetEditable(viewEditable: AbstractView) {
        val parent = viewEditable.parent as ViewGroup
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            if (view is AbstractView && view != viewEditable) {
                view.editable = false
            }
        }
    }

    fun setEditable(viewGroup: ViewGroup, editable: Boolean = false) {
        if (viewGroup.childCount < 1) {
            return
        }

        for (i in viewGroup.childCount - 1 downTo 0) {
            val view = viewGroup.getChildAt(i)
            if (view is AbstractView) {
                view.editable = editable
            }
        }
    }

    fun resort(viewGroup: ViewGroup, event: MotionEvent): Boolean {
        if (viewGroup.childCount < 1) {
            return false
        }

        for (i in viewGroup.childCount - 1 downTo 0) {
            val view = viewGroup.getChildAt(i)
            if (view is AbstractView && view.isTouched(event) >= 0) {
                view.editable = true
                viewGroup.removeViewAt(i)

                if (view is PolygonView) {
                    viewGroup.addView(view, 0)
                } else {
                    viewGroup.addView(view)
                }
                view.checkButtons()
                return true
            }
        }

        return false
    }
}