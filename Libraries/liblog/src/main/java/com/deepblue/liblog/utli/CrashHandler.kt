package com.deepblue.liblog.utli

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.os.SystemClock

import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.lang.reflect.Field
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.HashMap


/**
 * 异常日志捕捉保存本地
 */
class CrashHandler : Thread.UncaughtExceptionHandler {

    companion object {

        private var instance: CrashHandler? = null

        @JvmStatic
        fun getInstance(): CrashHandler {
            if (instance == null) {
                instance = CrashHandler()
            }
            return instance!!
        }
    }

    private var MAX_CRASHFILE = 100

    private var path: String? = null
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

    private var mContext: Context? = null
    private val infos = HashMap<String, String>()
    private val formatter = SimpleDateFormat("yyyy-MM-dd")


    fun init(context: Context, max_file: Int, outPath: String? = null){
        mContext = context
        if (max_file > 0) {
            MAX_CRASHFILE = max_file
        }
        path = KLogUtil.getDiskCachePath(mContext) + "/Crash_Log"
        if (outPath != null) {
            path = outPath
        }

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }


    /**
     * 限制文件数量
     */
    fun autoClear() {
        val dir = File(path)
        if (dir == null || !dir.exists() || !dir.isDirectory) {
            return
        }
        val listFiles = dir.listFiles()
        if (listFiles.size > MAX_CRASHFILE) {
            listFiles[0].delete()
        }
    }


    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler!!.uncaughtException(thread, ex)
        } else {
            SystemClock.sleep(3000)
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }
    }


    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) return false
        try {
            object : Thread() {
                override fun run() {
                    Looper.prepare()
                    Looper.loop()
                }
            }.start()
            autoClear()
            collectDeviceInfo(mContext!!)
            saveCrashInfoFile(ex)
            SystemClock.sleep(3000)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }

    fun collectDeviceInfo(ctx: Context) {
        try {
            val pm = ctx.packageManager
            val pi = pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = pi.versionName + ""
                val versionCode = pi.versionCode.toString() + ""
                infos["versionName"] = versionName
                infos["versionCode"] = versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {

        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos[field.name] = field.get(null).toString()
            } catch (e: Exception) {
            }

        }
    }


    @Throws(Exception::class)
    private fun saveCrashInfoFile(ex: Throwable): String? {
        val sb = StringBuffer()
        try {
            val sDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = sDateFormat.format(Date())
            sb.append("\r\n" + date + "\n")
            for ((key, value) in infos) {
                sb.append("$key=$value\n")
            }
            val writer = StringWriter()
            val printWriter = PrintWriter(writer)
            ex.printStackTrace(printWriter)
            var cause: Throwable? = ex.cause
            while (cause != null) {
                cause.printStackTrace(printWriter)
                cause = cause.cause
            }
            printWriter.flush()
            printWriter.close()
            val result = writer.toString()
            sb.append(result)
            return writeFile(sb.toString())
        } catch (e: Exception) {
            sb.append("an error occured while writing file...\r\n")
            writeFile(sb.toString())
        }

        return null
    }

    @Throws(Exception::class)
    private fun writeFile(sb: String): String {
        val time = formatter.format(Date())
        val fileName = "crash-$time.log"


        val dir = File(path)
        if (!dir.exists()) dir.mkdirs()
        val fos = FileOutputStream("$path/$fileName", true)
        fos.write(sb.toByteArray())
        fos.flush()
        fos.close()
        return fileName
    }


}
