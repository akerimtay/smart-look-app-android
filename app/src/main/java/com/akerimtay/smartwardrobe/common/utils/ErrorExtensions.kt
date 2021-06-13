package com.akerimtay.smartwardrobe.common.utils

import androidx.annotation.StringRes
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.akerimtay.smartwardrobe.common.model.ErrorMessage

fun Throwable.getErrorMessage(@StringRes defaultResId: Int): ErrorMessage =
    when (this) {
        is BaseError -> this.getErrorMessage(defaultResId = defaultResId)
        else -> ErrorMessage(resId = defaultResId)
    }