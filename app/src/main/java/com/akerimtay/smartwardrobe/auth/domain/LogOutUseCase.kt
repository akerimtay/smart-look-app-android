package com.akerimtay.smartwardrobe.auth.domain

import com.akerimtay.smartwardrobe.auth.SessionManager
import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.network.NetworkManager
import com.akerimtay.smartwardrobe.user.domain.gateway.UserLocalGateway
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class LogOutUseCase(
    private val userLocalGateway: UserLocalGateway,
    private val sessionManager: SessionManager,
    private val authRemoteGateway: AuthRemoteGateway,
    private val networkManager: NetworkManager
) : UseCase<Unit, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Unit) {
        if (networkManager.isConnectedToNetwork()) authRemoteGateway.logOut()
        sessionManager.userId?.let { userId ->
            userLocalGateway.deleteById(userId)
            sessionManager.clearSession()
        }
        Timber.d("log out")
    }
}