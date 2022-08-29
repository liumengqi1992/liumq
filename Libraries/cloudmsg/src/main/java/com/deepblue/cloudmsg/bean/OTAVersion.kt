package com.deepblue.cloudmsg.bean

import android.content.Context
import android.content.pm.PackageInfo
import android.text.TextUtils

class OTAVersion {
    var packageId = 0
    var packageName = ""
    var packageVersion = ""
    var downloadUrl = ""
    var comments = ""

    fun getVersionName(): String {
        if (TextUtils.isEmpty(packageVersion) || !packageVersion.contains('_')) {
            return ""
        }
        val index = packageVersion.indexOf('_')
        return packageVersion.substring(0, index)
    }

    fun getVersionCode(): Int {
        if (TextUtils.isEmpty(packageVersion) || !packageVersion.contains('_')) {
            return 0
        }
        val index = packageVersion.indexOf('_')
        val code = packageVersion.substring(index + 1)
        return try {
            code.toInt()
        } catch (e: Exception) {
            0
        }
    }

    fun hasNewVersion(versionCode: Int): Boolean {
        return versionCode < getVersionCode()
    }
}