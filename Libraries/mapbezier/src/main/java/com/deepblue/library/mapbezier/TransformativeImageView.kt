package com.deepblue.library.mapbezier

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.graphics.PointF
import com.deepblue.library.utils.Duration
import com.deepblue.library.utils.Instant

class TransformativeImageView : com.deepblue.library.transform.TransformativeImageView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val clickPointF = PointF(0F, 0F)
    private var clickTime = Instant.now()
    var clickListener: ClickListener? = null

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                clickPointF.x = event.x
                clickPointF.y = event.y
                clickTime = Instant.now()
                clickListener?.onTouched(clickPointF)
            }
            MotionEvent.ACTION_MOVE -> {
                if (distance(clickPointF, PointF(event.x, event.y)) > 50) {
                    clickPointF.x = 0F
                    clickPointF.y = 0F
                }
            }
            MotionEvent.ACTION_UP -> {
                if (clickPointF.x > 0 && clickPointF.y > 0) {
                    clickPointF.x = event.x
                    clickPointF.y = event.y
                    //点击动作
                    val duration = Duration.between(clickTime, Instant.now()).getSeconds()
                    if (duration < 0.5) {
                        clickListener?.onClick(clickPointF)
                    } else {
                        clickListener?.onLongClick(clickPointF)
                    }
                }
            }
        }
        return true
    }
}