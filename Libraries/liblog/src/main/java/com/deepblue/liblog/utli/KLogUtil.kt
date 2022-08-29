package com.deepblue.liblog.utli

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import com.deepblue.liblog.BaseLog
import com.deepblue.liblog.CLog

/**
 * Created by zhaokaiqiang on 15/12/11.
 */
object KLogUtil {

    fun isEmpty(line: String): Boolean {
        return TextUtils.isEmpty(line) || line == "\n" || line == "\t" || TextUtils.isEmpty(line.trim { it <= ' ' })
    }

    fun printLine(type: Int, tag: String, isTop: Boolean, needFile: Boolean? = false) {
        when (type) {
            BaseLog.V ->
                if (isTop) {
                    Log.v(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════")
                } else {
                    Log.v(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════")
                }
            BaseLog.D ->
                if (isTop) {
                    Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════")
                } else {
                    Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════")
                }
            BaseLog.I ->
                if (isTop) {
                    Log.i(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════")
                } else {
                    Log.i(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════")
                }
            BaseLog.W ->
                if (isTop) {
                    Log.w(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════")
                } else {
                    Log.w(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════")
                }
            BaseLog.E ->
                if (isTop) {
                    Log.e(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════")
                } else {
                    Log.e(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════")
                }
            BaseLog.A ->
                if (isTop) {
                    Log.wtf(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════")
                } else {
                    Log.wtf(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════")
                }
        }

        if (isTop) {
            if (needFile != null && needFile) {
                BaseLog.printFile(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════")
            }
        } else {
            if (needFile != null && needFile) {
                BaseLog.printFile(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════")
            }
        }

    }

    /**
     * 有sd卡获取sd卡跟目录，
     * 没有sd卡，应用目录
     */
    fun getDiskCachePath(context: Context?): String {
        return if (Environment.MEDIA_MOUNTED === Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            context!!.externalCacheDir!!.path
        } else {
            Environment.getExternalStorageDirectory().path
        }

    }


}
