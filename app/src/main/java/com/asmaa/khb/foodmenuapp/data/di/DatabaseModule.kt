package com.asmaa.khb.foodmenuapp.data.di

import androidx.room.Room
import com.asmaa.khb.foodmenuapp.data.databases.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    /* provide the dao */
    single { get<AppDatabase>().categoriesDao() }
    single { get<AppDatabase>().productsDao() }
}