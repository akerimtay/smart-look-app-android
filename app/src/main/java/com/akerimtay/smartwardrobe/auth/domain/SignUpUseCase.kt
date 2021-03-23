package com.akerimtay.smartwardrobe.auth.domain

import com.akerimtay.smartwardrobe.auth.model.Gender
import com.akerimtay.smartwardrobe.auth.model.User
import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.network.NetworkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.*

class SignUpUseCase(
    private val networkManager: NetworkManager,
    private val firebaseService: FirebaseService
) : UseCase<SignUpUseCase.Param, Unit>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param) {
        networkManager.throwIfNoConnection()
        val firebaseUser = firebaseService.signUpWithEmailAndPassword(
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
        firebaseService.createUser(user)
    }

    data class Param(
        val name: String,
        val gender: Gender,
        val email: String,
        val birthDate: Date?,
        val password: String
    )
}