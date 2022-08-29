package com.deepblue.library.bezier

import android.content.Context
import android.graphics.Paint
import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.deepblue.library.bezier.utils.PointUtils
import com.deepblue.library.bezier.utils.ViewUtils
import com.deepblue.library.utils.Duration
import com.deepblue.library.utils.Instant
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.LinkedList

abstract class AbstractView(context: Context) : View(context) {

    protected var currentPointIndex = -1//当前选中的端点序号
    private var downPointIndex = -1//ACTION_DOWN时的端点序号
    protected val paint = Paint()
    val points = ArrayList<Point>()
    var editable = false
    private val pointDown = Point(0, 0)

    private var lastTime: Instant? = null
    private val lastPoint = Point(0,0)
    //点击起点后封闭
    protected var closed = false

    init {
        //抗锯齿
        paint.isAntiAlias = true
    }

    //当前步骤序号
    private var indexStep = 0
    private val steps = LinkedList<ArrayList<Point>>()
    private val stepsClosed = LinkedList<Boolean>()
    private var btnPrevious: Button? = null
    private var btnNext: Button? = null

    protected var clickListener: ClickListener? = null

    open fun onDestroy() {
        steps.clear()
        btnPrevious = null
        btnNext = null
    }

    fun isTouched(event: MotionEvent): Int {
        for (i in 0 until points.size) {
            if (PointUtils.isInCircle(event.x, event.y, points[i])) {
                editable = true
                ViewUtils.resetEditable(this)
                return i
            }
        }
        return -1
    }

    fun setButtons(btnPrevious: Button, btnNext: Button) {
        this.btnPrevious = btnPrevious
        this.btnNext = btnNext

        checkButtons()
    }

    abstract fun clickPoint(point: Point)
    abstract fun addPoint(point: Point)

    open fun init(clickListener: ClickListener) {
        this.clickListener = clickListener
        points.clear()
        editable = true
        addStep()
    }

    fun addStep() {
        doAsync {

            removeStep()
            val temps = ArrayList<Point>()
            for (i in 0 until points.size) {
                val temp = Point(points[i].x, points[i].y)
                temps.add(temp)
            }
            steps.addLast(temps)
            stepsClosed.addLast(closed)
            indexStep = steps.size - 1

            uiThread {

                checkButtons()
            }
        }
    }

    fun checkButtons() {
        btnPrevious?.isEnabled = hasPreviousStep()
        btnNext?.isEnabled = hasNextStep()

        this.btnPrevious?.setOnClickListener {
            previousStep()
            checkButtons()
        }

        this.btnNext?.setOnClickListener {
            nextStep()
            checkButtons()
        }
    }

    private fun removeStep() {
        while (steps.size > indexStep + 1) {
            steps.removeLast()
            stepsClosed.removeLast()
        }
    }

    private fun hasPreviousStep(): Boolean = indexStep > 0

    private fun hasNextStep(): Boolean = indexStep < steps.size - 1

    private fun previousStep() {
        indexStep --
        if (indexStep < 0) {
            indexStep = 0
        } else {
            recover()
        }
    }

    private fun nextStep() {
        indexStep ++
        if (indexStep >= steps.size) {
            indexStep = steps.size - 1
        } else {
            recover()
        }
    }

    private fun recover() {
        doAsync {

            points.clear()
            for (i in 0 until steps[indexStep].size) {
                val temp = Point(steps[indexStep][i].x, steps[indexStep][i].y)
                points.add(temp)
            }
            closed = stepsClosed[indexStep]
            uiThread {

                invalidate()
            }
        }
    }

    protected fun downEmpty(event: MotionEvent) {
        lastTime = Instant.now()
        pointDown.x = event.x.toInt()
        pointDown.y = event.y.toInt()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (editable) {
                    currentPointIndex = isTouched(event)
                    if (currentPointIndex >= 0) {
                        downPointIndex = currentPointIndex
                        clickListener?.onTouched(points[downPointIndex])
                        lastTime = Instant.now()
                        lastPoint.x = points[currentPointIndex].x
                        lastPoint.y = points[currentPointIndex].y
                        return true
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (downPointIndex < 0 || !PointUtils.isInCircle(event.x, event.y, lastPoint)) {
                    downPointIndex = -1
                }
                if (pointDown.x > 0 && pointDown.y > 0 && !PointUtils.isInCircle(event.x, event.y, pointDown)) {
                    pointDown.x = 0
                    pointDown.y = 0
                }
            }
            MotionEvent.ACTION_UP -> {
                val duration = Duration.between(lastTime!!, Instant.now()).getSeconds()
                if (duration < 1) {
                    if (downPointIndex >= 0) {
                        editable = true
                        clickListener?.onClick(points[downPointIndex])
                    } else if (pointDown.x > 0 && pointDown.y > 0) {
                        clickListener?.onClickEmpty(pointDown)
                    }
                } else if (duration <= 2) {
                    if (downPointIndex >= 0) {
                        clickListener?.onLongClick(points[downPointIndex])
                    } else if (pointDown.x > 0 && pointDown.y > 0) {
                        clickListener?.onLongClickEmpty(pointDown)
                    }
                }
                downPointIndex = -1
            }
        }
        return false
    }
}