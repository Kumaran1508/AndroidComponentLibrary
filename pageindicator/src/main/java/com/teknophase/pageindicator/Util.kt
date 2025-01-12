package com.teknophase.pageindicator

import android.content.Context
import android.util.DisplayMetrics
import androidx.compose.ui.unit.Dp

fun Dp.toPx(context: Context): Float {
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    return this.value * (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}