package com.akerimtay.smartwardrobe

import com.akerimtay.smartwardrobe.auth.AuthModule
import com.akerimtay.smartwardrobe.network.NetworkModule
import org.koin.core.module.Module

object KoinModules {
    fun create(): List<Module> = listOf(
        AuthModule.create(),
        NetworkModule.create()
    )
}