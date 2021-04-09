package com.akerimtay.smartwardrobe.user.domain.gateway

import androidx.lifecycle.LiveData
import com.akerimtay.smartwardrobe.user.model.User

interface UserLocalGateway {
    suspend fun save(user: User)
    suspend fun getById(id: String): User?
    suspend fun getByIdAsFlow(id: String): LiveData<User?>
    suspend fun deleteById(id: String)
}