package com.akerimtay.smartwardrobe.auth

import com.akerimtay.smartwardrobe.auth.domain.FirebaseAuthService
import com.akerimtay.smartwardrobe.auth.domain.SignInUseCase
import com.akerimtay.smartwardrobe.auth.ui.SignInViewModel
import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AuthModule : InjectionModule {
    override fun create(): Module = module {
        viewModel { SignInViewModel(get()) }
        single { FirebaseAuthService(FirebaseAuth.getInstance()) }
        single { SignInUseCase(get(), get()) }
    }
}