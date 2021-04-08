package com.akerimtay.smartwardrobe.user

import android.graphics.Bitmap
import com.akerimtay.smartwardrobe.auth.data.model.FirebaseUserResponse
import com.akerimtay.smartwardrobe.user.data.db.UserEntity
import com.akerimtay.smartwardrobe.user.model.Gender
import com.akerimtay.smartwardrobe.user.model.User

object UserConverter {
    fun fromNetwork(user: FirebaseUserResponse, bitmap: Bitmap?): User =
        User(
            id = user.id.orEmpty(),
            name = user.name.orEmpty(),
            gender = Gender.toGender(user.gender),
            email = user.email.orEmpty(),
            birthDate = user.birthDate,
            image = bitmap
        )

    fun toNetwork(user: User, imageUrl: String?): FirebaseUserResponse =
        FirebaseUserResponse(
            id = user.id,
            name = user.name,
            gender = user.gender.serializedName,
            email = user.email,
            birthDate = user.birthDate,
            imageUrl = imageUrl
        )

    fun fromDatabase(entity: UserEntity): User =
        User(
            id = entity.id,
            name = entity.name,
            gender = entity.gender,
            email = entity.email,
            birthDate = entity.birthDate,
            image = entity.image
        )

    fun toDatabase(user: User): UserEntity =
        UserEntity(
            id = user.id,
            name = user.name,
            gender = user.gender,
            email = user.email,
            birthDate = user.birthDate,
            image = user.image
        )
}