package com.akerimtay.smartwardrobe.user.domain.gateway

import com.akerimtay.smartwardrobe.user.model.User
import kotlinx.coroutines.flow.Flow

interface UserLocalGateway {
    suspend fun save(user: User)
    suspend fun getById(id: String): User?
    fun getByIdAsFlow(id: String): Flow<User?>
    suspend fun deleteById(id: String)
}