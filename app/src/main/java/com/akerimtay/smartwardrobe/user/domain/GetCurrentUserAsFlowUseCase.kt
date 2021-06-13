package com.akerimtay.smartwardrobe.user.domain

import com.akerimtay.smartwardrobe.auth.SessionManager
import com.akerimtay.smartwardrobe.common.base.UseCaseSync
import com.akerimtay.smartwardrobe.user.domain.gateway.UserLocalGateway
import com.akerimtay.smartwardrobe.user.model.User
import kotlinx.coroutines.flow.Flow

class GetCurrentUserAsFlowUseCase(
    private val sessionManager: SessionManager,
    private val userLocalGateway: UserLocalGateway,
) : UseCaseSync<Unit, Flow<User?>>() {
    override fun execute(parameters: Unit): Flow<User?> =
        userLocalGateway.getByIdAsFlow(sessionManager.userId.orEmpty())
}