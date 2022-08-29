package com.deepblue.library.mapselector.bean

import android.graphics.Bitmap

data class MapBean(val label: String, val bitmap: Bitmap, var isChecked: Boolean, val isLocked: Boolean)