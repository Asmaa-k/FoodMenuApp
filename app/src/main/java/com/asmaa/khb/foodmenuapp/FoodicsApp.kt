package com.asmaa.khb.foodmenuapp

import android.app.Application
import com.asmaa.khb.foodmenuapp.data.di.databaseModule
import com.asmaa.khb.foodmenuapp.data.di.networkModule
import com.asmaa.khb.foodmenuapp.data.di.repositoryModule
import com.asmaa.khb.foodmenuapp.presentation.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin


class FoodicsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@FoodicsApp)
            modules(networkModule, databaseModule, repositoryModule, appModule)
        }
    }
}