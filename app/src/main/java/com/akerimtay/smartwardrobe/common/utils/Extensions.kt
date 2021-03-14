package com.akerimtay.smartwardrobe.common.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.akerimtay.smartwardrobe.R
import org.koin.ext.getFullName

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

fun FragmentManager.replaceFragment(
    @IdRes containerViewId: Int = R.id.content,
    fragment: Fragment,
    tag: String? = fragment.tag,
    backStackName: String = fragment::class.getFullName(),
    shouldAddToBackStack: Boolean = true
) {
    commit {
        replace(containerViewId, fragment, tag)
        if (shouldAddToBackStack) addToBackStack(backStackName)
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

inline fun <T> LiveData<T>.observeNotNull(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    this.observe(owner) { it?.run(observer) }
}