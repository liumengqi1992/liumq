package com.deepblue.advert

import android.content.Context
import android.os.Environment
import java.io.*

object AssetsUtils {

    private fun getDiskCachePath(context: Context): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            if (context.externalCacheDir == null) {
                context.cacheDir.path
            } else {
                context.externalCacheDir!!.path
            }
        } else {
            context.cacheDir.path
        }
    }

    @Throws(IOException::class)
    fun doCopy(context: Context, assetsFileName: String): String {

        val desPath = getDiskCachePath(context) + "/video-cache/"
        val outFileName = desPath + assetsFileName
        File(outFileName).mkdir()
        val file = File(outFileName)
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        return try {
            val inputStream = context.assets.open(assetsFileName)
            copyAndClose(inputStream, FileOutputStream(outFileName))
            outFileName
        } catch (e: IOException) {//if directory fails exception
            e.printStackTrace()
            ""
        }
    }

    private fun closeQuietly(out: OutputStream?) {
        try {
            out?.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    private fun closeQuietly(`is`: InputStream?) {
        try {
            `is`?.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

    }

    @Throws(IOException::class)
    private fun copyAndClose(`is`: InputStream, out: OutputStream) {
        copy(`is`, out)
        closeQuietly(`is`)
        closeQuietly(out)
    }

    @Throws(IOException::class)
    private fun copy(`is`: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var n = `is`.read(buffer)
        while (-1 != n) {
            out.write(buffer, 0, n)
            n = `is`.read(buffer)
        }
    }
}