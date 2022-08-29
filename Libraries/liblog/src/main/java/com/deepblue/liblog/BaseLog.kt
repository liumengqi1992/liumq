package com.deepblue.liblog

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.deepblue.liblog.database.MqttDatabase
import com.deepblue.liblog.database.MqttMsg
import com.deepblue.liblog.utli.FileLog
import com.deepblue.liblog.utli.KLogUtil
import com.deepblueai.mqtt.MqttManager
import org.jetbrains.anko.doAsync
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.lang.Thread.sleep
import java.text.SimpleDateFormat

import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory


object BaseLog {
    val LINE_SEPARATOR = System.getProperty("line.separator")

    val V = 0x1
    val D = 0x2
    val I = 0x3
    val W = 0x4
    val E = 0x5
    val A = 0x6
    var mContext: Context? = null
    var mUrl: String = ""

    var MAX_LENGTH = 1000
    private var mIsGlobalTagEmpty = true
    private val TAG_DEFAULT = "BaseLog"


    val JSON_INDENT = 4
    private val STACK_TRACE_INDEX_5 = 5
    private val STACK_TRACE_INDEX_4 = 4
    var mDeviceCode: String = ""

    private val NULL_TIPS = "Log with null object"

    private var mGlobalTag: String? = null

    var fileMsgList = ArrayList<String>()
    var isWriting: Boolean = false
    var mqttSending: Boolean = false

    var mqttManagers: MqttManager? = null
    var topic: String = ""
    val sDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS")


    val newThreadPool by lazy {
        Executors.newCachedThreadPool(object : ThreadFactory {
            override fun newThread(r: Runnable?): Thread {
                val thread = Thread(r)
                thread.setUncaughtExceptionHandler { t, e ->
                    println(t.name)
                    e.printStackTrace()
                }
                return thread
            }
        })
    }

    fun setMqttManager(mMqttManager: MqttManager) {
        this.mqttManagers = mMqttManager
    }

    /**
     * Log初始化
     * isSHow:是否需要显示，执行Log模块
     */

    @JvmStatic
    fun initMqtt(context: Context, deviceCode: String, mqttTopic: String) {
        mContext = context
        topic = mqttTopic
        if (deviceCode.isNotEmpty()) {
            mDeviceCode = deviceCode
        }
    }

    /**
     *设置log 写入文件目录
     */
    @JvmStatic
    fun setFilePath(context: Context, url: String? = null) {
        mContext = context
        if (url != null && url.length > 0) {
            mUrl = url;
        } else {
            mUrl = KLogUtil.getDiskCachePath(mContext) + "/BaseLog"
        }

        CLog.e("ss", mUrl)
    }


    fun doWhileWirting() {
        doAsync {
            while (fileMsgList != null && fileMsgList.size > 0) {
                sleep(5000)
                isWriting = true
                var msgList = ArrayList<String>()
                msgList.addAll(fileMsgList)
                if (save(msgList)) {
                    fileMsgList.clear()
                    if (fileMsgList.size > msgList.size) {
                        fileMsgList.removeAll(msgList)
                    } else {
                        fileMsgList.clear()
                    }
                }
            }
            isWriting = false
        }
    }

    fun doWhileMqtt() {
        if (!mqttSending && mqttManagers != null) {
            mqttSending = true
            doAsync {
                while (mqttSending) {
                    sleep(50)
                    var mqttMsg = MqttDatabase.getDatabase(mContext!!).getMqttDao().getFirst()
                    if (mqttMsg != null && mDeviceCode.isNotEmpty() && topic.isNotEmpty()) {
                        mqttManagers!!.publishMessage(topic, pingMqttMsg(mqttMsg))
                    } else {
                        mqttSending = false
                    }
                    sleep(100)
                    MqttDatabase.getDatabase(mContext!!).getMqttDao().delete(mqttMsg)

                }
                mqttSending = false
            }
        }
    }

    fun pingMqttMsg(mqttMsg: MqttMsg): String {
        var json = JSONObject()
        json.put("deviceCode", mDeviceCode)
        json.put("time", mqttMsg.sendTime)
        json.put("message", mqttMsg.message)
        return json.toString()

    }

    fun insertMqtt(tag: String, sub: String) {
        if (MLog.mShow) {
            newThreadPool.execute {
                var mqtt = MqttMsg()
                val date = sDateFormat.format(Date())
                mqtt.sendTime = date
                mqtt.message = "{" + tag + "} " + sub;
                MqttDatabase.getDatabase(mContext!!).getMqttDao().insert(mqtt)
            }
        }
    }


    @JvmStatic
    fun printDefault(type: Int, tag: String, msg: String, needFile: Boolean? = true) {
        var index = 0
        val length = msg.length
        val countOfSub = length / MAX_LENGTH

        if (countOfSub > 0) {
            for (i in 0 until countOfSub) {
                val sub = msg.substring(index, index + MAX_LENGTH)
                printSub(type, tag, sub, needFile)
                index += MAX_LENGTH
            }
            printSub(type, tag, msg.substring(index, length), needFile)
        } else {
            printSub(type, tag, msg, needFile)
        }
    }


    @JvmStatic
    fun printJson(type: Int, tag: String, msg: String, needFile: Boolean? = false) {
        val contents = wrapperContent(STACK_TRACE_INDEX_5, tag, msg)
        val tag = contents[0]
        val msg = contents[1]
        val headString = contents[2]

        var message = try {
            when {
                msg.startsWith("{") -> {
                    val jsonObject = JSONObject(msg)
                    jsonObject.toString(BaseLog.JSON_INDENT)
                }
                msg.startsWith("[") -> {
                    val jsonArray = JSONArray(msg)
                    jsonArray.toString(BaseLog.JSON_INDENT)
                }
                else -> msg
            }
        } catch (e: JSONException) {
            msg
        }

        KLogUtil.printLine(type, tag, true, needFile)
        message = headString + BaseLog.LINE_SEPARATOR + message
        val lines = message.split(BaseLog.LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (line in lines) {
            printDefault(type, tag, "║ $line", needFile)
        }
        KLogUtil.printLine(type, tag, false, needFile)
    }


    fun printFile(tagStr: String, objectMsg: String) {
        if (mContext != null) {
            val allMessage = StringBuffer()
            allMessage.append("\n")
            val date = sDateFormat.format(Date())
            allMessage.append(date)
            allMessage.append(" ")
            allMessage.append(mContext!!.packageName)
            allMessage.append(" ")
            allMessage.append(tagStr)
            allMessage.append(": ")
            allMessage.append(objectMsg)
            fileMsgList.add(allMessage.toString())
//            save(allMessage.toString())
        }

    }

    private fun save(msgs: List<String>): Boolean {
        if (mContext != null && msgs != null && msgs.size > 0) {
            val file = File(mUrl)
            CLog.e("ss", "path:" + file.path)
            if (!file.exists()) {
                file.mkdirs()
//                file.createNewFile()
            }
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val time = formatter.format(Date())
            val fileName = "log-$time.log"
            return try {
                val outputStream = FileOutputStream(file.path + "/" + fileName, true)
                val outputStreamWriter = OutputStreamWriter(outputStream, "UTF-8")
                for (msg in msgs) {
                    outputStreamWriter.write(msg)
                }
                outputStreamWriter.flush()
                outputStream.close()
                true
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                false
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                false
            } catch (e: IOException) {
                e.printStackTrace()
                false
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        } else {
            return false
        }
    }

    private fun printSub(type: Int, tag: String, sub: String, needFile: Boolean? = true) {
        if (CLog.cShow) {
            when (type) {
                V -> Log.v(tag, sub)
                D -> Log.d(tag, sub)
                I -> Log.i(tag, sub)
                W -> Log.w(tag, sub)
                E -> Log.e(tag, sub)
                A -> Log.wtf(tag, sub)
            }
        }

        if (needFile != null && needFile && FLog.fShow) {
            printFile(tag, sub)
            if (fileMsgList.size > 0 && !isWriting) {
                doWhileWirting()
            }
        }
    }


    private fun wrapperContent(stackTraceIndex: Int, tagStr: String?, msg: String): Array<String> {

        val stackTrace = Thread.currentThread().stackTrace

        val targetElement = stackTrace[stackTraceIndex]
        //获取类文件扩展名
        val fileName = targetElement.fileName
        val SUFFIX = getSUFFIX(fileName)
        var className = targetElement.className
        val classNameInfo = className.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (classNameInfo.size > 0) {
            className = classNameInfo[classNameInfo.size - 1] + SUFFIX
        }

        if (className.contains("$")) {
            className = className.split("\\$".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] + SUFFIX
        }

        val methodName = targetElement.methodName
        var lineNumber = targetElement.lineNumber

        if (lineNumber < 0) {
            lineNumber = 0
        }

        var tag = tagStr ?: className

        if (mIsGlobalTagEmpty && TextUtils.isEmpty(tag)) {
            tag = TAG_DEFAULT
        } else if (!mIsGlobalTagEmpty) {
            tag = mGlobalTag
        }

        val message = if (msg == null) NULL_TIPS else msg
        val headString = "[ ($className:$lineNumber)#$methodName ] "

        return arrayOf(tag, message, headString)
    }

    private fun getSUFFIX(fileName: String): String {
        val index = fileName.indexOf('.')
        return if (index < 0) {
            ""
        } else fileName.substring(index)
    }


}
