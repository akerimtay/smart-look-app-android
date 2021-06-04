package com.akerimtay.smartwardrobe.favorites

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.database.AppDatabase
import com.akerimtay.smartwardrobe.favorites.data.db.FavoriteDatabase
import com.akerimtay.smartwardrobe.favorites.domain.DeleteFavoriteByIdUseCase
import com.akerimtay.smartwardrobe.favorites.domain.DeleteFavoriteByOutfitIdUseCase
import com.akerimtay.smartwardrobe.favorites.domain.FavoriteLocalGateway
import com.akerimtay.smartwardrobe.favorites.domain.GetFavoritesByUserIdAsFlowUseCase
import com.akerimtay.smartwardrobe.favorites.domain.IsFavoriteOutfitUseCase
import com.akerimtay.smartwardrobe.favorites.domain.SaveFavoriteUseCase
import com.akerimtay.smartwardrobe.favorites.ui.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object FavoriteModule : InjectionModule {
    override fun create(): Module = module {
        viewModel { FavoritesViewModel(get(), get()) }

        single { get<AppDatabase>().favoriteDao() }
        single<FavoriteLocalGateway> { FavoriteDatabase(get()) }

        single { SaveFavoriteUseCase(get()) }
        single { DeleteFavoriteByIdUseCase(get()) }
        single { DeleteFavoriteByOutfitIdUseCase(get(), get()) }
        single { GetFavoritesByUserIdAsFlowUseCase(get()) }
        single { IsFavoriteOutfitUseCase(get(), get()) }
    }
}