package com.deepblue.library.components

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView
import android.view.MotionEvent

/**
 * 可点击空白处的GridView
 */
class TouchBlankGridView : GridView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, android.R.attr.gridViewStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mTouchBlankPosListener: OnTouchBlankPositionListener? = null

    interface OnTouchBlankPositionListener {
        /**
         *
         * @return 是否要终止事件的路由
         */
        fun onTouchBlankPosition()
    }

    fun setOnTouchBlankPositionListener(listener: OnTouchBlankPositionListener) {
        mTouchBlankPosListener = listener
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (mTouchBlankPosListener != null) {
            if (!isEnabled) {
                // A disabled view that is clickable still consumes the touch
                // events, it just doesn't respond to them.
                return isClickable || isLongClickable
            }

            if (event.actionMasked == MotionEvent.ACTION_UP) {
                val motionPosition = pointToPosition(event.x.toInt(), event.y.toInt())
                if (motionPosition == INVALID_POSITION) {
                    mTouchBlankPosListener?.onTouchBlankPosition()
                    return true
                }
            }
        }

        return super.onTouchEvent(event)
    }
}