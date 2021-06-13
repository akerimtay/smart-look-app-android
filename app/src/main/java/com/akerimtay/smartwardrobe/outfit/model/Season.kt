package com.akerimtay.smartwardrobe.outfit.model

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.akerimtay.smartwardrobe.R

@Keep
enum class Season(
    val serializedName: String,
    @StringRes val titleResId: Int
) {
    WINTER("winter", R.string.winter),
    DEMI_SEASON("demiSeason", R.string.demi_season),
    SUMMER("summer", R.string.summer);

    companion object {
        fun toSeason(value: String?): Season = values().firstOrNull { value == it.serializedName } ?: SUMMER
    }
}