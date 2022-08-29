package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.JsonUtils
import com.deepblue.library.planbmsg.Response
import com.deepblue.library.planbmsg.bean.ErrorInfo
import com.deepblue.library.planbmsg.bean.LevelInfo
import org.json.JSONObject

/**
 * 通用参数查询
 */
class GetCommonParamsRes : Response() {

    fun getIntValue(key: String): Int? {
        val js = getJSONObject() ?: return null
        return try {
            js.getInt(key)
        } catch (e: Exception) {
            null
        }
    }

    fun getBooleanValue(key: String, defValue: Boolean): Boolean {
        val js = getJSONObject() ?: return defValue
        return try {
            js.getBoolean(key)
        } catch (e: Exception) {
            defValue
        }
    }

    fun getStringValue(key: String): String? {
        val js = getJSONObject() ?: return null
        return try {
            js.getString(key)
        } catch (e: Exception) {
            null
        }
    }

    private fun getJSONObject(): JSONObject? {
        val js = getJson() ?: return null
        return try {
            JSONObject(js.common_params)
        } catch (e: Exception) {
            null
        }
    }

    fun getJson(): Data? {
        return JsonUtils.fromJson(json.toString(), Data::class.java)
    }

    class Data {
        var common_params = ""
    }

    fun getJson2(): LevelInfo? {
        val levelInfo = JsonUtils.fromJson(json.toString(), LevelInfo::class.java) ?: return null
        return levelInfo
    }
}