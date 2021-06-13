package com.akerimtay.smartwardrobe.outfit.model

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.akerimtay.smartwardrobe.R

@Keep
enum class OutfitGender(
    val serializedName: String,
    @StringRes val titleResId: Int
) {
    MALE("male", R.string.male),
    FEMALE("female", R.string.female),
    UNISEX("unisex", R.string.unisex);

    companion object {
        fun toGender(value: String?): OutfitGender = values().firstOrNull { value == it.serializedName } ?: MALE
    }
}