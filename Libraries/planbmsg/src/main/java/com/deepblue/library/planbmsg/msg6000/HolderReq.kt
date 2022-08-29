package com.deepblue.library.planbmsg.msg6000

import com.deepblue.library.planbmsg.Request
import java.math.BigDecimal
import kotlin.math.abs

class HolderReq: Request(6000, "控制云台") {

    companion object {
        private const val UP_DOWN = "up_down"
        private const val LEFT_RIGHT = "left_right"
        private const val RESET = "reset"
    }

    private fun format(d: Double): Double {
        val bg = BigDecimal(d)
        return bg.setScale(1, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun up(angle: Double = 1.0): String {
        json = Data(UP_DOWN, abs(format(angle)))
        return toString()
    }

    fun down(angle: Double = -1.0): String {
        json = Data(UP_DOWN, -abs(format(angle)))
        return toString()
    }

    fun left(angle: Double = 1.0): String {
        json = Data(LEFT_RIGHT, abs(format(angle)))
        return toString()
    }

    fun right(angle: Double = -1.0): String {
        json = Data(LEFT_RIGHT, -abs(format(angle)))
        return toString()
    }

    fun reset(): String {
        json = Data(RESET, 0.0)
        return toString()
    }

    /**
     * 数据区
     * @param operate 方向。up_down-上下，left_right-左右
     * @param angle 需要旋转的角度。左偏为正，右偏为负。上偏为正，下偏为负。
     */
    class Data(val operate: String, val angle: Double)
}