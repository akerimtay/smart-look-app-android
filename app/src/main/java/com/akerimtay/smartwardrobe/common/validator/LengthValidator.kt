package com.akerimtay.smartwardrobe.common.validator

open class LengthValidator(
    private val minLength: Int,
    private val maxLength: Int
) : TextValidator {
    override fun isValid(text: String): Boolean = text.length in minLength..maxLength
}