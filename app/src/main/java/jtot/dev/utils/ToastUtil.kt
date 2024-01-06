package jtot.dev.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun showShortToast(
    context: Context,
    message: String,
) = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    .show()

fun showLongToast(
    context: Context,
    message: String,
) = Toast.makeText(context, message, Toast.LENGTH_LONG)
    .show()

/**
 *
 * <Usage>
 *
 * In Activity
 * showSnackBar(view = window.decorView, activity = this, message = "1")
 *
 * In Fragment
 * showSnackBar(view = requireView(), activity = requireActivity(), message = "1")
 *
 */

fun showSnackBar(
    view: View,
    activity: Activity,
    message: String,
) = view.let {
    Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
        .show()
    val imm: InputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun showSnackBarWithAction(
    view: View,
    activity: Activity,
    message: String,
    actionMessage: String,
    action: () -> Unit,
) = view.let {
    Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
        .setAction(actionMessage) {
            action
        }
        .show()
    val imm: InputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
