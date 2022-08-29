package com.deepblue.library.xml

import com.thoughtworks.xstream.XStream

object XMLUtils {

    fun fromXML(xml: String, root: Any, names: Array<String>?, types: Array<Class<*>>?): Any {
        val xStream = XStream()
        if (names != null && types != null && names.size == types.size) {
            for (i in 0 until names.size) {
                xStream.alias(names[i], types[i])
            }
        }
        return xStream.fromXML(xml, root)
    }

    fun toXML(`object`: Any, nameRoot: String, typeRoot: Class<*>): String {
        val xStream = XStream()
        xStream.alias(nameRoot, typeRoot)
        return xStream.toXML(`object`)
    }
}