package com.akerimtay.smartwardrobe.auth.domain

import com.google.firebase.auth.FirebaseUser

interface AuthRemoteGateway {
    suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseUser
    suspend fun signUpWithEmailAndPassword(email: String, password: String): FirebaseUser
    suspend fun restorePassword(email: String)
    suspend fun logOut()
}