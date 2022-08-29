package com.deepblue.library.planbmsg.msg2000

import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

class SetCommonParamsReq {

    fun intValue(map:HashMap<String,Any>): String {
        val ob = JSONObject()
        for ((key,value) in map){
            ob.put(key, value)
        }
        return getJson(ob)
    }

    fun booleanValue(key: String, value: Boolean): String {
        val ob = JSONObject()
        ob.put(key, value)
        return getJson(ob)
    }

    private fun getJson(ob: JSONObject): String {
        val json = JSONObject()
        json.put("common_params", ob)
        val params = JSONObject()
        params.put("type", 2016)
        params.put("name", "通用参数设置")
        params.put("number", 0)
        params.put("json", json)
        return params.toString()
    }
}