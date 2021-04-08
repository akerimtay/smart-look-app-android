package com.akerimtay.smartwardrobe.profileedit

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.profileedit.ui.ProfileEditViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object ProfileEditModule : InjectionModule {
    override fun create(): Module = module {
        viewModel { ProfileEditViewModel(get(), get()) }
    }
}