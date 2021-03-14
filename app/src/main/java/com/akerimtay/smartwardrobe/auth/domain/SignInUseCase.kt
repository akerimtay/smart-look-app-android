package com.akerimtay.smartwardrobe.auth.domain

import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.network.NetworkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SignInUseCase(
    private val networkManager: NetworkManager,
    private val firebaseAuthService: FirebaseAuthService
) : UseCase<SignInUseCase.Param, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param) {
        networkManager.throwIfNoConnection()
        val firebaseUser = firebaseAuthService.signInWithEmailAndPassword(
            email = parameters.email,
            password = parameters.password
        )
    }

    data class Param(val email: String, val password: String)
}