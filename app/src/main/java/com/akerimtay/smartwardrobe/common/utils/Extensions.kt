package com.akerimtay.smartwardrobe.common.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import java.util.*

inline fun <T> LiveData<T>.observeNotNull(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit,
) {
    this.observe(owner) { it?.run(observer) }
}

inline fun <T> T.applyIf(applyCondition: Boolean, block: T.() -> Unit): T {
    if (applyCondition) block(this)
    return this
}

fun Any?.isNull() = this == null

fun Double.isPositive(): Boolean = this > 0.0

fun String.capitalize(): String = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
}