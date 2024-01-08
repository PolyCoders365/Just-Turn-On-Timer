package jtot.dev.utils

import android.content.Context
import android.util.DisplayMetrics

/**
 dp값을 px로 변환
 */
fun Context.dpToPixel(dp: Float): Float {
    return dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}
