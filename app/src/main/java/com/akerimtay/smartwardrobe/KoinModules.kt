package com.akerimtay.smartwardrobe

import com.akerimtay.smartwardrobe.articles.ArticlesModule
import com.akerimtay.smartwardrobe.auth.AuthModule
import com.akerimtay.smartwardrobe.database.DatabaseModule
import com.akerimtay.smartwardrobe.feed.FeedModule
import com.akerimtay.smartwardrobe.network.NetworkModule
import com.akerimtay.smartwardrobe.outfit.OutfitModule
import com.akerimtay.smartwardrobe.profile.ProfileModule
import com.akerimtay.smartwardrobe.profileedit.ProfileEditModule
import com.akerimtay.smartwardrobe.splash.SplashModule
import com.akerimtay.smartwardrobe.user.UserModule
import com.akerimtay.smartwardrobe.weather.WeatherModule
import org.koin.core.module.Module

object KoinModules {
    fun create(): List<Module> = listOf(
        AppModule.create(),
        AuthModule.create(),
        SplashModule.create(),
        NetworkModule.create(),
        UserModule.create(),
        DatabaseModule.create(),
        ProfileModule.create(),
        ProfileEditModule.create(),
        FeedModule.create(),
        WeatherModule.create(),
        OutfitModule.create(),
        ArticlesModule.create(),
    )
}