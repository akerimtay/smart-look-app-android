package com.akerimtay.smartwardrobe.user.domain

import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.user.domain.gateway.UserLocalGateway
import com.akerimtay.smartwardrobe.user.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SaveUserUseCase(
    private val userLocalGateway: UserLocalGateway
) : UseCase<SaveUserUseCase.Param, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param) {
        userLocalGateway.save(parameters.user)
    }

    data class Param(val user: User)
}