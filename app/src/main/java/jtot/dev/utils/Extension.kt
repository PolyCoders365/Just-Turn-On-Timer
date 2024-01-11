package jtot.dev.utils

import android.content.Intent
import android.os.Build
import java.io.Serializable

/**
 *
 * 확장함수를 모아두는 곳
 *
 */

fun <T : Serializable> Intent.intentSerializable(
    key: String,
    clazz: Class<T>,
): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializableExtra(key, clazz)
    } else {
        this.getSerializableExtra(key) as T?
    }
}
