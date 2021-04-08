package com.akerimtay.smartwardrobe.user.data

import com.akerimtay.smartwardrobe.auth.data.model.FirebaseUserResponse
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.akerimtay.smartwardrobe.database.Converters
import com.akerimtay.smartwardrobe.user.UserConverter
import com.akerimtay.smartwardrobe.user.domain.gateway.UserRemoteGateway
import com.akerimtay.smartwardrobe.user.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

private const val USERS = "users"
private const val USERS_AVATARS = "user_avatars"
private const val FIVE_MEGABYTE = 1024 * 1024 * 5L

class UserService(
    private val database: FirebaseFirestore,
    private val storageReference: StorageReference
) : UserRemoteGateway {
    override suspend fun getUser(id: String): User {
        val task = database.collection(USERS)
            .document(id)
            .get()
            .await()
        val userResponse = task.toObject(FirebaseUserResponse::class.java) ?: throw BaseError.UserNotFound
        val bitmap = userResponse.imageUrl?.let {
            val reference = storageReference.child("$USERS_AVATARS/$it")
            val byteArray = reference.getBytes(FIVE_MEGABYTE).await()
            Converters().toBitmap(byteArray = byteArray)
        }
        return UserConverter.fromNetwork(user = userResponse, bitmap = bitmap)
    }

    override suspend fun saveUser(user: User) {
        database.collection(USERS)
            .document(user.id)
            .set(UserConverter.toNetwork(user, null))
            .await()
    }
}