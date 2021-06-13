package com.akerimtay.smartwardrobe.user.data.db

import com.akerimtay.smartwardrobe.user.UserConverter
import com.akerimtay.smartwardrobe.user.domain.gateway.UserLocalGateway
import com.akerimtay.smartwardrobe.user.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDatabase(
    private val userDao: UserDao
) : UserLocalGateway {
    override suspend fun save(user: User) {
        userDao.save(UserConverter.toDatabase(user))
    }

    override suspend fun getById(id: String): User? = userDao.getById(id)?.let { UserConverter.fromDatabase(it) }

    override fun getByIdAsFlow(id: String): Flow<User?> =
        userDao.getByIdAsFlow(id).map { userEntity -> userEntity?.let { UserConverter.fromDatabase(it) } }

    override suspend fun deleteById(id: String) {
        userDao.deleteById(id)
    }
}