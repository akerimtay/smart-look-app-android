package com.akerimtay.smartwardrobe.auth.domain

import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.network.NetworkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class RestorePasswordUseCase(
    private val networkManager: NetworkManager,
    private val firebaseService: FirebaseService
) : UseCase<RestorePasswordUseCase.Param, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param) {
        networkManager.throwIfNoConnection()
        firebaseService.restorePassword(email = parameters.email)
    }

    data class Param(val email: String)
}