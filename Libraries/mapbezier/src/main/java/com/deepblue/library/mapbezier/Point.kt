package com.deepblue.library.mapbezier

/**
 * 地图上的坐标点
 */
data class Point(val index: Int, var x: Double, var y: Double, var angle: Double = 0.0) {
    var name: String = if (index >= 0) "P$index" else ""
}