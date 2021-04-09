package com.akerimtay.smartwardrobe.user.domain.gateway

import android.graphics.Bitmap
import com.akerimtay.smartwardrobe.user.model.User

interface UserRemoteGateway {
    suspend fun getUser(id: String): User
    suspend fun saveUser(user: User)
    suspend fun uploadImage(fileName: String, bitmap: Bitmap): String
}