package com.asmaa.khb.foodmenuapp.presentation.di

import com.asmaa.khb.foodmenuapp.presentation.ui.viewmodel.TableViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel { TableViewModel(tableRepository = get()) }
}