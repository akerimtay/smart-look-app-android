package com.akerimtay.smartwardrobe.auth.model

import androidx.annotation.StringRes
import com.akerimtay.smartwardrobe.R

enum class Gender(
    val serializedName: String,
    @StringRes val displayName: Int
) {
    MALE("male", R.string.male),
    FEMALE("female", R.string.female);

    companion object {
        fun toGender(value: String?): Gender = values().firstOrNull { value == it.serializedName } ?: MALE
    }
}