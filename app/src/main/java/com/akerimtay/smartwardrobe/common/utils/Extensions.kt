package com.akerimtay.smartwardrobe.common.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

inline fun <T> LiveData<T>.observeNotNull(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    this.observe(owner) { it?.run(observer) }
}

inline fun <T> T.applyIf(applyCondition: Boolean, block: T.() -> Unit): T {
    if (applyCondition) block(this)
    return this
}

fun Any?.isNull() = this == null

fun Double.isPositive(): Boolean = this > 0.0
