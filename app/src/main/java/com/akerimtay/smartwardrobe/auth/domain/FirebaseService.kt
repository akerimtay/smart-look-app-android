package com.akerimtay.smartwardrobe.auth.domain

import com.akerimtay.smartwardrobe.auth.data.UserConverter
import com.akerimtay.smartwardrobe.auth.data.model.FirebaseUserResponse
import com.akerimtay.smartwardrobe.auth.model.User
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private const val USERS = "users"

class FirebaseService(
    private val auth: FirebaseAuth,
    private val database: FirebaseFirestore
) {
    suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseUser {
        val task = auth.signInWithEmailAndPassword(email, password).await()
        return task.user ?: throw BaseError.InvalidEmailOrPasswordError
    }

    suspend fun signUpWithEmailAndPassword(email: String, password: String): FirebaseUser {
        val task = auth.createUserWithEmailAndPassword(email, password).await()
        return task.user ?: throw BaseError.UserNotCreated
    }

    suspend fun restorePassword(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    suspend fun getUser(id: String): User {
        val task = database.collection(USERS)
            .document(id)
            .get()
            .await()
        val response = task.toObject(FirebaseUserResponse::class.java) ?: throw BaseError.UserNotFound
        return UserConverter.fromNetwork(response)
    }

    suspend fun createUser(user: User) {
        database.collection(USERS)
            .document(user.id)
            .set(UserConverter.toNetwork(user))
            .await()
    }
}