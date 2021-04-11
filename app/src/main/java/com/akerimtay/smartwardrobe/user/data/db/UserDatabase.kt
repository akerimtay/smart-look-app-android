package com.akerimtay.smartwardrobe.user.data.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.akerimtay.smartwardrobe.user.UserConverter
import com.akerimtay.smartwardrobe.user.domain.gateway.UserLocalGateway
import com.akerimtay.smartwardrobe.user.model.User

class UserDatabase(
    private val userDao: UserDao
) : UserLocalGateway {
    override suspend fun save(user: User) {
        userDao.save(UserConverter.toDatabase(user))
    }

    override suspend fun getById(id: String): User? = userDao.getById(id)?.let { UserConverter.fromDatabase(it) }

    override suspend fun getByIdAsFlow(id: String): LiveData<User?> =
        userDao.getByIdAsFlow(id).map { userEntity -> userEntity?.let { UserConverter.fromDatabase(it) } }

    override suspend fun deleteById(id: String) {
        userDao.deleteById(id)
    }
}