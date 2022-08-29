package com.deepblue.library.utils

import android.text.InputFilter
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import java.lang.Exception
import java.util.regex.Pattern

object EditFilterUtils {

    val inputFilterProhibitEmoji: InputFilter
        get() = InputFilter { source, start, end, dest, dstart, dend ->
            val buffer = StringBuffer()
            var i = start
            while (i < end) {
                val codePoint = source[i]
                if (!getIsEmoji(codePoint)) {
                    buffer.append(codePoint)
                } else {
                    i++
                    i++
                    continue
                }
                i++
            }
            if (source is Spanned) {
                val sp = SpannableString(buffer)
                TextUtils.copySpansFrom(source, start, end, null, sp, 0)
                sp
            } else {
                buffer
            }
        }

    @JvmStatic
    val filter: InputFilter
        get() = InputFilter { source, start, end, dest, dstart, dend ->
            val speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？卍卐囍]"
            val rome =
                "[^(?!_)(?!.*?_\$)[a-zA-Z0-9_\\u4e00-\\u9fa5]|M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})+\$]"
            val pattern = Pattern.compile(speChat)
            val romenum = Pattern.compile(rome)
            val matcher = pattern.matcher(source.toString())
            val matcher2 = romenum.matcher(source.toString())
            if (matcher.find() || matcher2.find()) ""
            else null
        }

    @JvmStatic
    val inputFilterProhibitSP: InputFilter
        get() = InputFilter { source, start, end, dest, dstart, dend ->
            val buffer = StringBuffer()
            var i = start
            while (i < end) {
                val codePoint = source[i]
                if (!getIsSp(codePoint)) {
                    buffer.append(codePoint)
                } else {
                    i++
                    i++
                    continue
                }
                i++
            }
            if (source is Spanned) {
                val sp = SpannableString(buffer)
                try {
                    TextUtils.copySpansFrom(source, start, end, null, sp, 0)
                    sp
                } catch (e: Exception) {
                    buffer
                }
            } else {
                buffer
            }
        }

    fun getIsEmoji(codePoint: Char): Boolean {
        return !(codePoint.toInt() == 0x0 || codePoint.toInt() == 0x9 || codePoint.toInt() == 0xA || codePoint.toInt() == 0xD || codePoint.toInt() >= 0x20 && codePoint.toInt() <= 0xD7FF || codePoint.toInt() >= 0xE000 && codePoint.toInt() <= 0xFFFD || codePoint.toInt() >= 0x10000 && codePoint.toInt() <= 0x10FFFF)

    }


    fun getIsSp(codePoint: Char): Boolean {
        return Character.getType(codePoint) > Character.LETTER_NUMBER
    }

    @JvmStatic
    var inputNoSpace = InputFilter { source, start, end, dest, dstart, dend ->

        if (source.contains(" ")) {
            source.toString().replace(" ", "")
        } else
            null

    }

    @JvmStatic
    fun getFilterLength(length: Int): InputFilter.LengthFilter {
        return InputFilter.LengthFilter(length)
    }


}