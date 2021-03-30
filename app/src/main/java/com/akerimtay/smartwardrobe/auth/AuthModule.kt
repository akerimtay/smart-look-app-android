package com.akerimtay.smartwardrobe.auth

import com.akerimtay.smartwardrobe.auth.data.AuthService
import com.akerimtay.smartwardrobe.auth.domain.AuthRemoteGateway
import com.akerimtay.smartwardrobe.auth.domain.RestorePasswordUseCase
import com.akerimtay.smartwardrobe.auth.domain.SignInUseCase
import com.akerimtay.smartwardrobe.auth.domain.SignUpUseCase
import com.akerimtay.smartwardrobe.auth.ui.forgotPassword.ForgotPasswordViewModel
import com.akerimtay.smartwardrobe.auth.ui.signIn.SignInViewModel
import com.akerimtay.smartwardrobe.auth.ui.signUp.SignUpViewModel
import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AuthModule : InjectionModule {
    override fun create(): Module = module {
        viewModel { SignInViewModel(get()) }
        viewModel { SignUpViewModel(get()) }
        viewModel { ForgotPasswordViewModel(get()) }
        single { FirebaseAuth.getInstance() }
        single<AuthRemoteGateway> { AuthService(get()) }
        single { SignInUseCase(get(), get(), get(), get()) }
        single { SignUpUseCase(get(), get(), get()) }
        single { RestorePasswordUseCase(get(), get()) }
    }
}