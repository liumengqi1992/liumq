package com.deepblue.library.utils

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern
import kotlin.math.min

/**
 * 汉字、数字和字母
 */
class SizeFilterWithChineseNumberLetter(private val letterMaxLength: Int) : InputFilter {
    companion object {
        private val chinesePattern = Pattern.compile("[\u4e00-\u9fa5]+")
        private val letterPattern = Pattern.compile("[0-9a-zA-Z]+")
        private const val SizeChinese = 3
    }

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val isAdd = dstart == dend
        if (!isAdd) {//删除文字不限制
            return null
        }
        val originLength = calculateLength(dest)

        val isChinese = chinesePattern.matcher(source.toString()).find()
        val isLetter = letterPattern.matcher(source.toString()).find()
        if (isChinese) {
            return when {
                originLength >= letterMaxLength -> ""
                originLength + source.length * SizeChinese > letterMaxLength -> {
                    var length = (letterMaxLength - originLength) / SizeChinese
                    if (length < 0) length = 0
                    length = min(length, source.length)
                    source.subSequence(0, length)
                }
                else -> null
            }
        } else if (isLetter) {
            return when {
                originLength >= letterMaxLength -> ""
                originLength + source.length > letterMaxLength -> {
                    var length = letterMaxLength - originLength
                    if (length < 0) length = 0
                    length = min(length, source.length)
                    source.subSequence(0, length)
                }
                else -> null
            }
        }

        return ""
    }

    private fun calculateLength(dest: Spanned): Int {
        var chineseLength = 0
        var letterLength = 0
        for (i in dest.indices) {
            if (chinesePattern.matcher(dest.subSequence(i, i + 1)).find()) {
                chineseLength += 3
            }
            if (letterPattern.matcher(dest.subSequence(i, i + 1)).find()) {
                letterLength += 1
            }
        }
        return chineseLength + letterLength
    }
}