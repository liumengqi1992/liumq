package com.deepblue.library.utils

import android.content.Context
import android.os.BatteryManager
import android.os.Build
import android.telephony.TelephonyManager
import android.app.AlarmManager
import android.app.PendingIntent
import java.net.NetworkInterface
import kotlin.system.exitProcess
import java.net.NetworkInterface.getNetworkInterfaces
import java.util.*


/**
 * Created by CaoJun on 2017/9/26.
 */
object DeviceUtils {

    /**
     * 在获取权限android.permission.READ_PHONE_STATE后调用该方法
     */
    @JvmStatic
    fun getIMEI(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                tm.imei
            } else {
                tm.deviceId
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
            ""
        }
    }

    @JvmStatic
    fun getBatteryManager(context: Context): BatteryManager? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        } else {
            null
        }
    }

    /**
     * 电量百分比
     */
    @JvmStatic
    fun getBatteryCapacity(context: Context): Int? {
        val bm = getBatteryManager(context)?:return null
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            null
        }
    }

    @JvmStatic
    fun reboot(context: Context, delayMillis: Long = 1000) {
        val intent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)
        val restartIntent = PendingIntent.getActivity(context.applicationContext, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val mgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // 1秒钟后重启应用
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + delayMillis, restartIntent)
        exitProcess(0)
    }

    /**
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET"/>
     * @return
     */
    fun getMacFromHardware(): String {
        try {
            val all = Collections.list(getNetworkInterfaces())
            for (nif in all) {
                if (nif.name != "wlan0") continue

                val macBytes = nif.hardwareAddress ?: return ""

                val res = StringBuilder()
                for (b in macBytes) {
                    res.append(String.format("%02X:", b))
                }

                if (res.isNotEmpty()) {
                    res.deleteCharAt(res.length - 1)
                }
                return res.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "02:00:00:00:00:00"
    }
}