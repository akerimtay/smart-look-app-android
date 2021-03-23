package com.akerimtay.smartwardrobe.auth.data

import com.akerimtay.smartwardrobe.auth.data.model.FirebaseUserResponse
import com.akerimtay.smartwardrobe.auth.model.User

object UserConverter {
    fun fromNetwork(user: FirebaseUserResponse): User =
        User(
            id = user.id.orEmpty(),
            name = user.name.orEmpty(),
            gender = user.gender.orEmpty(),
            email = user.email.orEmpty(),
            birthDate = user.birthDate
        )
}