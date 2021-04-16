package com.akerimtay.smartwardrobe.outfit

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.outfit.data.api.OutfitService
import com.akerimtay.smartwardrobe.outfit.domain.OutfitRemoteGateway
import org.koin.core.module.Module
import org.koin.dsl.module

object OutfitModule : InjectionModule {
    override fun create(): Module = module {
        single<OutfitRemoteGateway> { OutfitService(get()) }
    }
}