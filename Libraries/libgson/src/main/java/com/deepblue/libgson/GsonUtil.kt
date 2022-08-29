package com.deepblue.libgson

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken.*
import com.google.gson.stream.JsonWriter
import org.json.JSONObject.NULL
import java.io.IOException


/**
 * @author Lee
 * @Title: {Json工具类}
 * @Description:{使用 Gson 处理json数据}
 * @date 2019/11/14
 */
object GsonUtil {

    val gson =
        GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss")
//            .registerTypeAdapter(
//            object :
//                TypeToken<Map<String?, Any?>?>() {}.type,
//            MapTypeAdapter()
//        )
            .registerTypeAdapter(HashMap::class.java,
                JsonDeserializer<Any?> { json, typeOfT, context ->
                    val resultMap: HashMap<String, Any> = HashMap()
                    val jsonObject = json.asJsonObject
                    val entrySet =
                        jsonObject.entrySet()
                    for ((key, value) in entrySet) {
                        resultMap[key] = value
                    }
                    resultMap
                })
            .create()


    /**
     * Json String to Kotlin data Class
     *
     * @param json
     *
     * eg.  GsonUtil.fromJson<List<UserInfo>>(str)
     *      GsonUtil.fromJson<UserInfo>(str)
     */
    inline fun <reified T : Any> fromJson(json: String): T? {
        try {
            return gson.fromJson(json, T::class.java)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * Kotlin data Class to Json String
     * @param obj
     */
    inline fun <reified T : Any> toJson(obj: T): String {
        return gson.toJson(obj, T::class.java)
    }


    class MapTypeAdapter : TypeAdapter<Any?>() {
        @Throws(IOException::class)
        override fun read(reader: JsonReader): Any? {
            return when (reader.peek()) {
                BEGIN_ARRAY -> {
                    val list: MutableList<Any?> = ArrayList()
                    reader.beginArray()
                    while (reader.hasNext()) {
                        list.add(read(reader))
                    }
                    reader.endArray()
                    list
                }
                BEGIN_OBJECT -> {
                    val map: MutableMap<String, Any?> =
                        LinkedTreeMap()
                    reader.beginObject()
                    while (reader.hasNext()) {
                        map[reader.nextName()] = read(reader)
                    }
                    reader.endObject()
                    map
                }
                STRING -> reader.nextString()
                NUMBER -> {
                    /**
                     * 改写数字的处理逻辑，将数字值分为整型与浮点型。
                     */
                    val dbNum: Double = reader.nextDouble()
                    // 数字超过long的最大值，返回浮点类型
                    if (dbNum > Long.MAX_VALUE) {
                        return dbNum
                    }
                    // 判断数字是否为整数值
                    val lngNum = dbNum.toLong()
                    if (dbNum == lngNum.toDouble()) {
                        lngNum
                    } else {
                        dbNum
                    }
                }
                BOOLEAN -> reader.nextBoolean()
                NULL -> {
                    reader.nextNull()
                    null
                }
                else -> throw IllegalStateException()
            }
        }

        @Throws(IOException::class)
        override fun write(out: JsonWriter?, value: Any?) { // 序列化无需实现
        }

    }
}