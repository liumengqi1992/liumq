package com.deepblue.liblog

import com.deepblue.liblog.BaseLog.D
import com.deepblue.liblog.BaseLog.I
import com.deepblue.liblog.BaseLog.V
import com.deepblue.liblog.BaseLog.W
import com.deepblue.liblog.BaseLog.A
import com.deepblue.liblog.BaseLog.E


/**
 * 显示Log并写入文件（一天一条日志文件）
 * v,d,i,w,e,a 方法为一行显示
 * jv,jd,jw,je,ja 方法为如果是json 按json格式显示（方便查看）
 */
object FLog {
    var fShow:Boolean=true

    fun setShow(show:Boolean){
        fShow=show
    }


    fun v(tag: String, sub: String) {
        BaseLog.printDefault(V, tag, sub, true)
    }

    fun vj(tag: String, sub: String) {
        BaseLog.printJson(V, tag, sub, true)
    }

    fun d(tag: String, sub: String) {
        BaseLog.printDefault(D, tag, sub, true)
    }

    fun dj(tag: String, sub: String) {
        BaseLog.printJson(D, tag, sub, true)
    }

    fun i(tag: String, sub: String) {
        BaseLog.printDefault(I, tag, sub, true)
    }

    fun ij(tag: String, sub: String) {
        BaseLog.printJson(I, tag, sub, true)
    }


    fun w(tag: String, sub: String) {
        BaseLog.printDefault(W, tag, sub, true)
    }

    fun wj(tag: String, sub: String) {
        BaseLog.printJson(W, tag, sub, true)
    }

    fun e(tag: String, sub: String) {
        BaseLog.printDefault(E, tag, sub, true)
    }

    fun ej(tag: String, sub: String) {
        BaseLog.printJson(E, tag, sub, true)
    }


    fun a(tag: String, sub: String) {
        BaseLog.printDefault(A, tag, sub, true)
    }

    fun aj(tag: String, sub: String) {
        BaseLog.printJson(A, tag, sub, true)
    }
}