package com.deepblue.library.planbmsg

import com.alibaba.fastjson.JSON
import com.google.gson.Gson

object JsonUtils {
    const val NULL = "NULL"

    /**
     * 将空值替换成NULL，以免解析时出错
     */
    private fun markNullValue(json: String): String {
        return json.replace("\"\"", NULL)
    }

    @JvmStatic
    fun <T> fromJson(json: String, classOfT: Class<T>): T? {
        return try {
            JSON.parseObject(json, classOfT)
        } catch (e: Exception) {
            e.printStackTrace()

            try {
                val nullJson = markNullValue(json)
                Gson().fromJson<T>(nullJson, classOfT)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}