package com.akerimtay.smartwardrobe.auth.domain

import com.akerimtay.smartwardrobe.common.base.BaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseAuthService(private val auth: FirebaseAuth) {
    suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseUser {
        val task = auth.signInWithEmailAndPassword(email, password).await()
        return task.user ?: throw BaseError.InvalidEmailOrPasswordError
    }
}