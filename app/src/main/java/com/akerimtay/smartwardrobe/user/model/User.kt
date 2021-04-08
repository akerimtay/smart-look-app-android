package com.akerimtay.smartwardrobe.user.model

import android.graphics.Bitmap
import java.util.*

data class User(
    val id: String,
    val name: String,
    val gender: Gender,
    val email: String,
    val birthDate: Date?,
    val image: Bitmap? = null
)