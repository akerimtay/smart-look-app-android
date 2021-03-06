package com.akerimtay.smartwardrobe.auth.domain

import com.akerimtay.smartwardrobe.auth.SessionManager
import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.network.NetworkManager
import com.akerimtay.smartwardrobe.user.domain.gateway.UserLocalGateway
import com.akerimtay.smartwardrobe.user.domain.gateway.UserRemoteGateway
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class SignInUseCase(
    private val networkManager: NetworkManager,
    private val authRemoteGateway: AuthRemoteGateway,
    private val userRemoteGateway: UserRemoteGateway,
    private val userLocalGateway: UserLocalGateway,
    private val sessionManager: SessionManager,
) : UseCase<SignInUseCase.Param, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param) {
        networkManager.throwIfNoConnection()
        val firebaseUser = authRemoteGateway.signInWithEmailAndPassword(
            email = parameters.email,
            password = parameters.password
        )
        val user = userRemoteGateway.getUser(firebaseUser.uid)
        userLocalGateway.save(user)
        sessionManager.userId = user.id
        Timber.d("user: $user")
    }

    data class Param(val email: String, val password: String)
}