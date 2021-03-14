package com.akerimtay.smartwardrobe.common.base

import androidx.annotation.StringRes
import com.akerimtay.smartwardrobe.R

private const val NO_RES_ID = 0

abstract class BaseError(@StringRes val errorResId: Int = NO_RES_ID) : Throwable() {
    object NoInternetError : BaseError(errorResId = R.string.check_internet_connection)
    object InvalidEmailOrPasswordError : BaseError(errorResId = R.string.invalid_email_or_password)
}