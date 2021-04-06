package com.akerimtay.smartwardrobe.user.domain.gateway

import com.akerimtay.smartwardrobe.user.model.User

interface UserRemoteGateway {
    suspend fun getUser(id: String): User
    suspend fun saveUser(user: User)
}