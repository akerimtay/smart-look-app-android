package com.akerimtay.smartwardrobe.user.data

import com.akerimtay.smartwardrobe.auth.data.model.FirebaseUserResponse
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.akerimtay.smartwardrobe.database.Converters
import com.akerimtay.smartwardrobe.user.UserConverter
import com.akerimtay.smartwardrobe.user.domain.gateway.UserRemoteGateway
import com.akerimtay.smartwardrobe.user.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

private const val USERS = "users"
private const val USERS_AVATARS = "user_avatars"
private const val FIVE_MEGABYTE = 1024 * 1024 * 5L

class UserService(
    private val database: FirebaseFirestore,
    private val storage: FirebaseStorage
) : UserRemoteGateway {
    override suspend fun getUser(id: String): User {
        val userResponse = database.collection(USERS)
            .document(id)
            .get()
            .await()
            .toObject(FirebaseUserResponse::class.java) ?: throw BaseError.UserNotFound
        val bitmap = userResponse.imageUrl?.let {
            val storageReference = storage.reference.child("$USERS_AVATARS/$it")
            val byteArray = storageReference.getBytes(FIVE_MEGABYTE).await()
            Converters().byteArrayToBitmap(byteArray = byteArray)
        }
        return UserConverter.fromNetwork(user = userResponse, bitmap = bitmap)
    }

    override suspend fun saveUser(user: User) {
        val byteArray = Converters().bitmapToByteArray(user.image)
        val imageUrl = byteArray?.let {
            val storageReference = storage.reference.child("$USERS_AVATARS/${user.id}")
            val uploadTask = storageReference.putBytes(it).await()
            uploadTask.uploadSessionUri.toString()
        }
        database.collection(USERS)
            .document(user.id)
            .set(UserConverter.toNetwork(user, imageUrl = imageUrl))
            .await()
    }
}