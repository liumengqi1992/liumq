package com.socks.library.klog

import android.util.Log

import com.socks.library.KLog
import com.socks.library.KLogUtil

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by zhaokaiqiang on 15/11/18.
 */
object JsonLog {

    @JvmStatic
    fun printJson(tag: String, msg: String, headString: String) {

        var message = try {
            when {
                msg.startsWith("{") -> {
                    val jsonObject = JSONObject(msg)
                    jsonObject.toString(KLog.JSON_INDENT)
                }
                msg.startsWith("[") -> {
                    val jsonArray = JSONArray(msg)
                    jsonArray.toString(KLog.JSON_INDENT)
                }
                else -> msg
            }
        } catch (e: JSONException) {
            msg
        }

        KLogUtil.printLine(tag, true)
        message = headString + KLog.LINE_SEPARATOR + message
        val lines = message.split(KLog.LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (line in lines) {
            Log.d(tag, "║ $line")
        }
        KLogUtil.printLine(tag, false)
    }
}
