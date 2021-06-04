package com.akerimtay.smartwardrobe.outfit

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.database.AppDatabase
import com.akerimtay.smartwardrobe.outfit.data.api.OutfitService
import com.akerimtay.smartwardrobe.outfit.data.db.OutfitDatabase
import com.akerimtay.smartwardrobe.outfit.domain.GetOutfitByIdAsFlowUseCase
import com.akerimtay.smartwardrobe.outfit.domain.GetOutfitsUseCaseAsFlow
import com.akerimtay.smartwardrobe.outfit.domain.GetSimilarOutfitsUseCase
import com.akerimtay.smartwardrobe.outfit.domain.OutfitLocalGateway
import com.akerimtay.smartwardrobe.outfit.domain.OutfitRemoteGateway
import com.akerimtay.smartwardrobe.outfit.ui.OutfitDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object OutfitModule : InjectionModule {
    override fun create(): Module = module {
        single<OutfitRemoteGateway> { OutfitService(get()) }

        single { get<AppDatabase>().outfitDao() }
        single<OutfitLocalGateway> { OutfitDatabase(get(), get()) }

        single { GetOutfitsUseCaseAsFlow(get(), get()) }
        single { GetOutfitByIdAsFlowUseCase(get()) }
        single { GetSimilarOutfitsUseCase(get()) }

        viewModel { (outfitId: Long) -> OutfitDetailViewModel(outfitId = outfitId, get(), get()) }
    }
}