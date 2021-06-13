package com.akerimtay.smartwardrobe.common.base

import androidx.annotation.StringRes
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.common.model.ErrorMessage

private const val NO_RES_ID = 0

abstract class BaseError(
    override val message: String? = null,
    @StringRes val errorResId: Int = NO_RES_ID,
) : Throwable(message) {
    object NoInternetError : BaseError(errorResId = R.string.check_internet_connection)
    object InvalidEmailOrPasswordError : BaseError(errorResId = R.string.invalid_email_or_password)
    object UserNotFound : BaseError(errorResId = R.string.error_user_not_found)
    object UserNotCreated : BaseError(errorResId = R.string.error_user_not_created)

    fun getErrorMessage(@StringRes defaultResId: Int = NO_RES_ID): ErrorMessage =
        when {
            !message.isNullOrEmpty() -> ErrorMessage(message = message, resId = defaultResId)
            errorResId != NO_RES_ID -> ErrorMessage(resId = errorResId)
            else -> ErrorMessage(resId = defaultResId)
        }
}