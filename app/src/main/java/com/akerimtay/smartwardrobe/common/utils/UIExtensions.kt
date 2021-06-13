package com.akerimtay.smartwardrobe.common.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.akerimtay.smartwardrobe.common.model.ErrorMessage

private const val CLICK_DELAY_MILLIS = 500L

fun View.setThrottleOnClickListener(callback: (view: View) -> Unit) {
    var lastClickTime = 0L

    this.setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()

        if (currentTimeMillis - lastClickTime > CLICK_DELAY_MILLIS) {
            lastClickTime = currentTimeMillis
            callback.invoke(it)
        }
    }
}

fun Fragment.showToast(@StringRes messageResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    context?.toast(message = getString(messageResId), duration = duration)
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun View.showKeyboard() {
    requestFocus()
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .showSoftInput(this, 0)
}

fun Fragment.hideKeyboard() {
    view?.hideKeyboard()
}

fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

@Suppress("unused")
fun Fragment.isPermissionsGranted(context: Context, permissions: Array<String>): Boolean {
    return PermissionsUtils.verifyGrantResults(permissions.map {
        ContextCompat.checkSelfPermission(context, it)
    }.toIntArray())
}

fun Fragment.shouldShowRequestPermissionsRationale(permissions: Array<String>): Boolean {
    permissions.forEach {
        if (!shouldShowRequestPermissionRationale(it)) {
            return false
        }
    }
    return true
}

fun Context.dip(value: Int): Int = dipF(value).toInt()
fun Context.dipF(value: Int): Float = value * resources.displayMetrics.density

fun View.dip(value: Int): Int = context.dip(value)

fun Fragment.showErrorMessage(errorMessage: ErrorMessage) {
    context?.toast(errorMessage.message.takeIf { !it.isNullOrEmpty() } ?: getString(errorMessage.resId))
}