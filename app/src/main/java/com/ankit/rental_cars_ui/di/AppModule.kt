package com.ankit.rental_cars_ui.di

import com.ankit.rental_cars_ui.data.datastore.SettingsDataStore
import com.ankit.rental_cars_ui.data.repository.CarRepositoryImpl
import com.ankit.rental_cars_ui.domain.repository.CarRepository
import com.ankit.rental_cars_ui.presentation.viewmodel.HomeViewModel
import com.ankit.rental_cars_ui.presentation.viewmodel.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // DataStore
    single { SettingsDataStore(androidContext()) }
    
    // Repository
    single<CarRepository> { CarRepositoryImpl() }
    
    // ViewModels
    viewModel { HomeViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
} 