package com.akerimtay.smartwardrobe.user

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.user.data.UserService
import com.akerimtay.smartwardrobe.user.data.db.UserDatabase
import com.akerimtay.smartwardrobe.user.domain.SaveUserUseCase
import com.akerimtay.smartwardrobe.user.domain.gateway.UserLocalGateway
import com.akerimtay.smartwardrobe.user.domain.gateway.UserRemoteGateway
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.module.Module
import org.koin.dsl.module

object UserModule : InjectionModule {
    override fun create(): Module = module {
        single { FirebaseFirestore.getInstance() }
        single<UserRemoteGateway> { UserService(get()) }
        single<UserLocalGateway> { UserDatabase(get()) }
        single { SaveUserUseCase(get()) }
    }
}