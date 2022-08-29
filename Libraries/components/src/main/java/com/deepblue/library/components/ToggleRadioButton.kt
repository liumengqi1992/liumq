package com.deepblue.library.components

import android.widget.RadioGroup
import android.content.Context
import android.util.AttributeSet

/**
 *  点击后可取消的RadioButton
 */
class ToggleRadioButton : android.support.v7.widget.AppCompatRadioButton {

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, R.attr.radioButtonStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun toggle() {
        isChecked = !isChecked
        if (!isChecked) {
            (parent as RadioGroup).clearCheck()
        }
    }
}