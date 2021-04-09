package com.akerimtay.smartwardrobe.user.data

import android.graphics.Bitmap
import com.akerimtay.smartwardrobe.auth.data.model.FirebaseUserResponse
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.akerimtay.smartwardrobe.common.utils.BitmapUtils
import com.akerimtay.smartwardrobe.user.UserConverter
import com.akerimtay.smartwardrobe.user.domain.gateway.UserRemoteGateway
import com.akerimtay.smartwardrobe.user.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

private const val USERS = "users"

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
        return UserConverter.fromNetwork(userResponse)
    }

    override suspend fun uploadImage(fileName: String, bitmap: Bitmap): String {
        val byteArray = BitmapUtils.fromBitmap(bitmap)
        val storageReference = storage.reference.child(fileName)
        storageReference.putBytes(byteArray).await()
        return storageReference.downloadUrl.await().toString()
    }

    override suspend fun saveUser(user: User) {
        database.collection(USERS)
            .document(user.id)
            .set(UserConverter.toNetwork(user))
            .await()
    }
}