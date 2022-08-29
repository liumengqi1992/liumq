package com.deepblue.library.utils

import java.util.Date

/**
 * 计算时间差
 * 如果SDK版本大于等于26（Android 8.0），直接使用java.time.Duration
 */
object Duration {

    private var start: Instant? = null
    private var end: Instant? = null

    @JvmStatic
    fun between(start: Instant, end: Instant): Duration {
        this.start = start
        this.end = end
        return this
    }

    @JvmStatic
    fun getSeconds(): Long {
        val millis = toMillis()
        return millis / 1000
    }

    @JvmStatic
    fun toMillis(): Long {
        if (end == null || start == null) {
            return 0
        }
        val endDate = Date(end!!.time)
        val startDate = Date(start!!.time)
        return endDate.time - startDate.time
    }
}