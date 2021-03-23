package com.akerimtay.smartwardrobe.auth

import com.akerimtay.smartwardrobe.auth.domain.FirebaseService
import com.akerimtay.smartwardrobe.auth.domain.SignInUseCase
import com.akerimtay.smartwardrobe.auth.ui.signIn.SignInViewModel
import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AuthModule : InjectionModule {
    override fun create(): Module = module {
        viewModel { SignInViewModel(get()) }
        single { FirebaseAuth.getInstance() }
        single { FirebaseFirestore.getInstance() }
        single { FirebaseService(get(), get()) }
        single { SignInUseCase(get(), get()) }
    }
}