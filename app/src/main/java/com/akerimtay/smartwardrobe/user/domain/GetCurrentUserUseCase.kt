package com.akerimtay.smartwardrobe.user.domain

import androidx.lifecycle.LiveData
import com.akerimtay.smartwardrobe.auth.SessionManager
import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.user.domain.gateway.UserLocalGateway
import com.akerimtay.smartwardrobe.user.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetCurrentUserUseCase(
    private val sessionManager: SessionManager,
    private val userLocalGateway: UserLocalGateway
) : UseCase<Unit, LiveData<User?>>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Unit): LiveData<User?> =
        userLocalGateway.getByIdAsFlow(sessionManager.userId.orEmpty())
}