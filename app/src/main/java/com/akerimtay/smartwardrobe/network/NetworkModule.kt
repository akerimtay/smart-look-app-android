package com.akerimtay.smartwardrobe.network

import com.akerimtay.smartwardrobe.BuildConfig
import com.akerimtay.smartwardrobe.common.di.InjectionModule
import com.akerimtay.smartwardrobe.common.utils.applyIf
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

private const val DEFAULT_CONNECT_TIMEOUT_SECONDS = 15L
private const val DEFAULT_READ_TIMEOUT_SECONDS = 15L
private const val DEFAULT_WRITE_TIMEOUT_SECONDS = 15L

object NetworkModule : InjectionModule {
    override fun create(): Module = module {
        single { NetworkManager(get()) }
        single {
            OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(AuthInterceptor())
                .applyIf(BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                }
                .build()
        }
        single {
            val gson = GsonBuilder()
                .registerTypeAdapter(Date::class.java, DateTypeAdapter())
                .create()
            Retrofit.Builder()
                .baseUrl(BuildConfig.REST_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(get())
                .build()
        }
    }
}