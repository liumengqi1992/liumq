package com.deepblue.liblog


import com.deepblue.liblog.BaseLog.D
import com.deepblue.liblog.BaseLog.I
import com.deepblue.liblog.BaseLog.V
import com.deepblue.liblog.BaseLog.W
import com.deepblue.liblog.BaseLog.A
import com.deepblue.liblog.BaseLog.E


/**
 * 只显示Log
 * v,d,i,w,e,a 方法为一行显示
 * jv,jd,jw,je,ja 方法为如果是json 按json格式显示（方便查看）
 */
object CLog {
    var cShow: Boolean = true

    fun setShow(show: Boolean) {
        cShow = show
    }

    fun v(tag: String, sub: String) {
        BaseLog.printDefault(V, tag, sub)
    }

    fun vj(tag: String, sub: String) {
        BaseLog.printJson(V, tag, sub)
    }

    fun d(tag: String, sub: String) {
        BaseLog.printDefault(D, tag, sub)
    }

    fun dj(tag: String, sub: String) {
        BaseLog.printJson(D, tag, sub)
    }

    fun i(tag: String, sub: String) {
        BaseLog.printDefault(I, tag, sub)
    }

    fun ij(tag: String, sub: String) {
        BaseLog.printJson(I, tag, sub)
    }


    fun w(tag: String, sub: String) {
        BaseLog.printDefault(W, tag, sub)
    }

    fun wj(tag: String, sub: String) {
        BaseLog.printJson(W, tag, sub)
    }

    fun e(tag: String, sub: String) {
        BaseLog.printDefault(E, tag, sub)
    }

    fun ej(tag: String, sub: String) {
        BaseLog.printJson(E, tag, sub)
    }


    fun a(tag: String, sub: String) {
        BaseLog.printDefault(A, tag, sub)
    }

    fun aj(tag: String, sub: String) {
        BaseLog.printJson(A, tag, sub)
    }
}