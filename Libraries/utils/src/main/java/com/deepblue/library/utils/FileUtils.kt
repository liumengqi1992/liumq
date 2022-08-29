package com.deepblue.library.utils

import android.content.Context
import android.os.Environment
import android.provider.MediaStore.MediaColumns
import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.content.ContentValues
import android.util.Base64
import java.io.*

object FileUtils {

    fun getDiskCachePath(context: Context): String {
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

    fun getUri(context: Context, imageFile: File): Uri? {
        val filePath = imageFile.absolutePath
        val cursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.Media._ID), MediaStore.Images.Media.DATA + "=? ",
                arrayOf(filePath), null)
        return if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            val baseUri = Uri.parse("content://media/external/images/media")
            Uri.withAppendedPath(baseUri, "" + id)
        } else {
            if (imageFile.exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, filePath)
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } else {
                null
            }
        }
    }

    fun getPath(uri: Uri, contentResolver: ContentResolver): String {
        val filePath: String
        val filePathColumn = arrayOf(MediaColumns.DATA)

        val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
        //      也可用下面的方法拿到cursor
        //      Cursor cursor = this.context.managedQuery(selectedVideoUri, filePathColumn, null, null, null);

        cursor!!.moveToFirst()

        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        filePath = cursor.getString(columnIndex)
        cursor.close()
        return filePath
    }

    /**
     * 将字符串保存到文件
     *
     * @param context
     * @param bytes
     * @param fileName
     * @throws Exception
     */
    fun saveFile(context: Context, bytes: ByteArray, fileName: String): String {
        return try {
            val targetPath = "${getDiskCachePath(context)}/$fileName"
            val out = FileOutputStream(targetPath)
            out.write(bytes)
            out.close()
            targetPath
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 将Base64字符解码保存到文件
     *
     * @param context
     * @param base64
     * @param fileName
     * @param flags
     * @throws Exception
     */
    fun base64SaveFile(context: Context, base64: String, fileName: String, flags: Int = Base64.NO_WRAP): String {
        val bytes = Base64.decode(base64, flags)
        return saveFile(context, bytes, fileName)
    }

    /**
     * 从assets读取文件内容
     */
    fun readAssets(context: Context, fileName: String): ArrayList<String> {
        val list = ArrayList<String>()
        try {
            val `is` = context.assets.open(fileName)
            val br = BufferedReader(InputStreamReader(`is`))
            var strLine = br.readLine()

            while (strLine != null) {
                list.add(strLine)
                strLine = br.readLine()
            }
            br.close()
            `is`.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }
}