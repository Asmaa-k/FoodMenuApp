package com.asmaa.khb.foodmenuapp.data.di

import com.asmaa.khb.foodmenuapp.data.apis.ApiService
import com.asmaa.khb.foodmenuapp.data.apis.ApiServiceImpl
import com.asmaa.khb.foodmenuapp.data.repositories.TableRepository
import com.asmaa.khb.foodmenuapp.data.repositories.TableRepositoryImp
import org.koin.dsl.module


val repositoryModule = module {
    factory<ApiService> { ApiServiceImpl(get()) }
    factory<TableRepository> {
        TableRepositoryImp(apiService = get(), categoriesDao = get(), productsDao = get())
    }
}
