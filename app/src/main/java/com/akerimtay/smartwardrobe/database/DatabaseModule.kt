package com.akerimtay.smartwardrobe.database

import androidx.room.Room
import com.akerimtay.smartwardrobe.common.di.InjectionModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

private const val DATABASE_NAME = "smartLook.db"

object DatabaseModule : InjectionModule {
    override fun create(): Module = module {
        single {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, DATABASE_NAME)
                //TODO add migrations
                .fallbackToDestructiveMigration()
                .fallbackToDestructiveMigrationOnDowngrade()
                .build()
        }
        single { get<AppDatabase>().userDao() }
    }
}