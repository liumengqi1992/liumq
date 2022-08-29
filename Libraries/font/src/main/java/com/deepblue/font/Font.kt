package com.deepblue.font

/**
 * 字体初始化类，主要用于设置当前需要加载的字体文件。需要在使用自定义字体控件前调用
 *
 */
object Font {
    const val PING_FANG_FONT = "fonts/Pingfang.ttf"

    var textFont: String = ""

    /**
     * 初始化字体库
     *
     */
    fun initFontLib(textFont: String) {
        this.textFont = textFont
    }
}