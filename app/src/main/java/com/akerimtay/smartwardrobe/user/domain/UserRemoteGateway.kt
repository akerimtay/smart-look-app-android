package com.akerimtay.smartwardrobe.user.domain

import com.akerimtay.smartwardrobe.user.model.User

interface UserRemoteGateway {
    suspend fun getUser(id: String): User
    suspend fun createUser(user: User)
}