package com.akerimtay.smartwardrobe.user

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.user.data.UserService
import com.akerimtay.smartwardrobe.user.data.db.UserDatabase
import com.akerimtay.smartwardrobe.user.domain.GetCurrentUserUseCase
import com.akerimtay.smartwardrobe.user.domain.UpdateUserUseCase
import com.akerimtay.smartwardrobe.user.domain.gateway.UserLocalGateway
import com.akerimtay.smartwardrobe.user.domain.gateway.UserRemoteGateway
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.koin.core.module.Module
import org.koin.dsl.module

object UserModule : InjectionModule {
    override fun create(): Module = module {
        single { FirebaseFirestore.getInstance() }
        single { Firebase.storage.reference }
        single<UserRemoteGateway> { UserService(get(), get()) }
        single<UserLocalGateway> { UserDatabase(get()) }
        single { UpdateUserUseCase(get(), get()) }
        single { GetCurrentUserUseCase(get(), get()) }
    }
}