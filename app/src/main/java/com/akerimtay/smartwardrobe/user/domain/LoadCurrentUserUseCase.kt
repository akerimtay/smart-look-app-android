package com.akerimtay.smartwardrobe.user.domain

import com.akerimtay.smartwardrobe.auth.SessionManager
import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.network.NetworkManager
import com.akerimtay.smartwardrobe.user.domain.gateway.UserLocalGateway
import com.akerimtay.smartwardrobe.user.domain.gateway.UserRemoteGateway
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class LoadCurrentUserUseCase(
    private val sessionManager: SessionManager,
    private val networkManager: NetworkManager,
    private val userRemoteGateway: UserRemoteGateway,
    private val userLocalGateway: UserLocalGateway,
) : UseCase<Unit, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Unit) {
        networkManager.throwIfNoConnection()
        val user = userRemoteGateway.getUser(sessionManager.userId.orEmpty())
        userLocalGateway.save(user)
    }
}