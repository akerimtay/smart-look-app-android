package com.akerimtay.smartwardrobe.content.model

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.akerimtay.smartwardrobe.R

@Keep
enum class ActionMenuType(@DrawableRes val iconResId: Int, @StringRes val titleResId: Int) {
    CHOOSE_IMAGE(iconResId = R.drawable.ic_twotone_image_white, titleResId = R.string.choose_from_gallery),
    DELETE_IMAGE(iconResId = R.drawable.ic_twotone_delete_white, titleResId = R.string.delete),
}