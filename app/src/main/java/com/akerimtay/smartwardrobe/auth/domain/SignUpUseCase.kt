package com.akerimtay.smartwardrobe.auth.domain

import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.network.NetworkManager
import com.akerimtay.smartwardrobe.user.domain.UserRemoteGateway
import com.akerimtay.smartwardrobe.user.model.Gender
import com.akerimtay.smartwardrobe.user.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.*

class SignUpUseCase(
    private val networkManager: NetworkManager,
    private val authRemoteGateway: AuthRemoteGateway,
    private val userRemoteGateway: UserRemoteGateway
) : UseCase<SignUpUseCase.Param, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param) {
        networkManager.throwIfNoConnection()
        val firebaseUser = authRemoteGateway.signUpWithEmailAndPassword(
            email = parameters.email,
            password = parameters.password
        )
        val user = User(
            id = firebaseUser.uid,
            name = parameters.name,
            gender = parameters.gender,
            email = parameters.email,
            birthDate = parameters.birthDate
        )
        userRemoteGateway.createUser(user)
    }

    data class Param(
        val name: String,
        val gender: Gender,
        val email: String,
        val birthDate: Date?,
        val password: String
    )
}