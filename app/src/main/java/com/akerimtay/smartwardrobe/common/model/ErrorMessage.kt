package com.akerimtay.smartwardrobe.common.model

import androidx.annotation.StringRes

data class ErrorMessage(
    @StringRes val resId: Int,
    val message: String? = null,
)