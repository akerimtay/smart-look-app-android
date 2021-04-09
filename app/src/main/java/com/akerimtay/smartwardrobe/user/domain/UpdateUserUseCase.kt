package com.akerimtay.smartwardrobe.user.domain

import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.user.domain.gateway.UserLocalGateway
import com.akerimtay.smartwardrobe.user.domain.gateway.UserRemoteGateway
import com.akerimtay.smartwardrobe.user.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class UpdateUserUseCase(
    private val userRemoteGateway: UserRemoteGateway,
    private val userLocalGateway: UserLocalGateway
) : UseCase<UpdateUserUseCase.Param, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param) {
        userRemoteGateway.saveUser(parameters.user)
        userLocalGateway.save(parameters.user)
        Timber.d("user: ${parameters.user}")
    }

    data class Param(val user: User)
}