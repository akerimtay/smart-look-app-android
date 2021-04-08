package com.akerimtay.smartwardrobe.profileedit

import com.akerimtay.smartwardrobe.common.validator.LengthValidator

object ProfileEditValidator {
    val nameValidator = LengthValidator(minLength = 3, maxLength = 50)
}