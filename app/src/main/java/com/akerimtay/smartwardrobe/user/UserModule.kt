package com.akerimtay.smartwardrobe.user

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.database.AppDatabase
import com.akerimtay.smartwardrobe.user.data.UserService
import com.akerimtay.smartwardrobe.user.data.db.UserDatabase
import com.akerimtay.smartwardrobe.user.domain.GetCurrentUserAsFlowUseCase
import com.akerimtay.smartwardrobe.user.domain.LoadCurrentUserUseCase
import com.akerimtay.smartwardrobe.user.domain.UpdateUserUseCase
import com.akerimtay.smartwardrobe.user.domain.UploadImageUseCase
import com.akerimtay.smartwardrobe.user.domain.gateway.UserLocalGateway
import com.akerimtay.smartwardrobe.user.domain.gateway.UserRemoteGateway
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.core.module.Module
import org.koin.dsl.module

object UserModule : InjectionModule {
    override fun create(): Module = module {
        single { FirebaseFirestore.getInstance() }
        single { FirebaseStorage.getInstance() }
        single<UserRemoteGateway> { UserService(get(), get()) }

        single { get<AppDatabase>().userDao() }
        single<UserLocalGateway> { UserDatabase(get()) }

        single { UpdateUserUseCase(get(), get()) }
        single { GetCurrentUserAsFlowUseCase(get(), get()) }
        single { UploadImageUseCase(get()) }
        single { LoadCurrentUserUseCase(get(), get(), get(), get()) }
    }
}