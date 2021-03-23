package com.akerimtay.smartwardrobe.auth.model

import java.util.*

data class User(
    val id: String,
    val name: String,
    val gender: String,
    val email: String,
    val birthDate: Date?
)