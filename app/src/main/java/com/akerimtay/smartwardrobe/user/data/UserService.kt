package com.akerimtay.smartwardrobe.user.data

import com.akerimtay.smartwardrobe.auth.data.model.FirebaseUserResponse
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.akerimtay.smartwardrobe.user.UserConverter
import com.akerimtay.smartwardrobe.user.domain.UserRemoteGateway
import com.akerimtay.smartwardrobe.user.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private const val USERS = "users"

class UserService(
    private val database: FirebaseFirestore
) : UserRemoteGateway {
    override suspend fun getUser(id: String): User {
        val task = database.collection(USERS)
            .document(id)
            .get()
            .await()
        val response = task.toObject(FirebaseUserResponse::class.java) ?: throw BaseError.UserNotFound
        return UserConverter.fromNetwork(response)
    }

    override suspend fun createUser(user: User) {
        database.collection(USERS)
            .document(user.id)
            .set(UserConverter.toNetwork(user))
            .await()
    }
}