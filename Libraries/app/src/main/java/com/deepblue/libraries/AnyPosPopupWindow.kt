package com.deepblue.libraries

import android.content.Context
import android.widget.PopupWindow
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*

class AnyPosPopupWindow(context: Context) : PopupWindow(context) {

    init {
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = true
        isFocusable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val contentView = LayoutInflater.from(context).inflate(
            R.layout.dialog_edit_menu,
            null, false
        )
        setContentView(contentView)
    }

    fun showAtLocation(parent: View, x: Int, y: Int) {

        val centerX = parent.width / 2
        val centerY = parent.height / 2
        showAtLocation(parent, Gravity.CENTER, x - centerX, y - centerY)
    }
}