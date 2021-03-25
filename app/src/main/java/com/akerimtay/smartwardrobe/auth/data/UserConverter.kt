package com.akerimtay.smartwardrobe.auth.data

import com.akerimtay.smartwardrobe.auth.data.model.FirebaseUserResponse
import com.akerimtay.smartwardrobe.auth.model.Gender
import com.akerimtay.smartwardrobe.auth.model.User

object UserConverter {
    fun fromNetwork(user: FirebaseUserResponse): User =
        User(
            id = user.id.orEmpty(),
            name = user.name.orEmpty(),
            gender = Gender.toGender(user.gender),
            email = user.email.orEmpty(),
            birthDate = user.birthDate
        )

    fun toNetwork(user: User): FirebaseUserResponse =
        FirebaseUserResponse(
            id = user.id,
            name = user.name,
            gender = user.gender.serializedName,
            email = user.email,
            birthDate = user.birthDate
        )
}