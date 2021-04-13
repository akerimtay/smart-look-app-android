package com.akerimtay.smartwardrobe.weather

import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.database.AppDatabase
import com.akerimtay.smartwardrobe.weather.data.api.WeatherRestApi
import com.akerimtay.smartwardrobe.weather.data.api.WeatherService
import com.akerimtay.smartwardrobe.weather.data.db.WeatherDatabase
import com.akerimtay.smartwardrobe.weather.domain.GetCurrentWeatherUseCase
import com.akerimtay.smartwardrobe.weather.domain.LoadWeatherUseCase
import com.akerimtay.smartwardrobe.weather.domain.gateway.WeatherLocalGateway
import com.akerimtay.smartwardrobe.weather.domain.gateway.WeatherRemoteGateway
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

object WeatherModule : InjectionModule {
    override fun create(): Module = module {
        single { get<Retrofit>().create(WeatherService::class.java) }
        single<WeatherRemoteGateway> { WeatherRestApi(get()) }

        single { get<AppDatabase>().weatherDao() }
        single<WeatherLocalGateway> { WeatherDatabase(get()) }

        single { LoadWeatherUseCase(get(), get(), get(), get()) }
        single { GetCurrentWeatherUseCase(get()) }
    }
}