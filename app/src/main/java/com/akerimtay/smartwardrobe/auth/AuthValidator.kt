package com.akerimtay.smartwardrobe.auth

import android.util.Patterns
import com.akerimtay.smartwardrobe.common.validator.LengthValidator
import java.util.regex.Pattern

private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"

object AuthValidator {
    val emailValidator = object : LengthValidator(minLength = 1, maxLength = 100) {
        override fun isValid(text: String): Boolean =
            super.isValid(text) && Patterns.EMAIL_ADDRESS.matcher(text).matches()
    }

    val passwordValidator = object : LengthValidator(minLength = 6, maxLength = 100) {
        override fun isValid(text: String): Boolean =
            super.isValid(text) && Pattern.compile(PASSWORD_PATTERN).matcher(text).matches()
    }

    val nameValidator = LengthValidator(minLength = 3, maxLength = 50)
}