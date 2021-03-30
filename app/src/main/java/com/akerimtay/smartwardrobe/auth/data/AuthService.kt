package com.akerimtay.smartwardrobe.auth.data

import com.akerimtay.smartwardrobe.auth.domain.AuthRemoteGateway
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthService(
    private val auth: FirebaseAuth
) : AuthRemoteGateway {
    override suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseUser {
        val task = auth.signInWithEmailAndPassword(email, password).await()
        return task.user ?: throw BaseError.InvalidEmailOrPasswordError
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String): FirebaseUser {
        val task = auth.createUserWithEmailAndPassword(email, password).await()
        return task.user ?: throw BaseError.UserNotCreated
    }

    override suspend fun restorePassword(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }
}