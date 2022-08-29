package com.deepblue.liblog

import com.deepblue.liblog.BaseLog.doWhileMqtt
import com.deepblue.liblog.BaseLog.insertMqtt

object MLog {

    var mShow: Boolean = true

    fun setShow(show: Boolean) {
        mShow = show
    }


    /**
     * tag:Tag
     * sub：Log内容
     * needFile:是否以文件形式备份本地（默认为true)
     */
    fun v(tag: String, sub: String, needFile: Boolean? = null) {
        BaseLog.printDefault(BaseLog.V, tag, sub, needFile)
        insertMqtt(tag, sub)
        doWhileMqtt()

    }

    fun d(tag: String, sub: String, needFile: Boolean? = null) {
        BaseLog.printDefault(BaseLog.D, tag, sub, needFile)
        insertMqtt(tag, sub)
        doWhileMqtt()
    }

    fun i(tag: String, sub: String, needFile: Boolean? = null) {
        BaseLog.printDefault(BaseLog.I, tag, sub, needFile)
        insertMqtt(tag, sub)
        doWhileMqtt()
    }

    fun w(tag: String, sub: String, needFile: Boolean? = null) {
        BaseLog.printDefault(BaseLog.W, tag, sub, needFile)
        insertMqtt(tag, sub)
        doWhileMqtt()
    }

    fun e(tag: String, sub: String, needFile: Boolean? = null) {
        BaseLog.printDefault(BaseLog.E, tag, sub, needFile)
        insertMqtt(tag, sub)
        doWhileMqtt()
    }

    fun a(tag: String, sub: String, needFile: Boolean? = null) {
        BaseLog.printDefault(BaseLog.A, tag, sub, needFile)
        insertMqtt(tag, sub)
        doWhileMqtt()
    }

}