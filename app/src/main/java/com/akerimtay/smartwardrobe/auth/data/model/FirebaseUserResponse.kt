package com.akerimtay.smartwardrobe.auth.data.model

import java.util.*

data class FirebaseUserResponse(
    val id: String? = null,
    val name: String? = null,
    val gender: String? = null,
    val email: String? = null,
    val birthDate: Date? = null,
    val imageUrl: String? = null
)