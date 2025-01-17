package com.ankit.rental_cars_ui.di

import com.ankit.rental_cars_ui.data.datastore.SettingsDataStore
import com.ankit.rental_cars_ui.data.repository.CarRepositoryImpl
import com.ankit.rental_cars_ui.domain.repository.CarRepository
import com.ankit.rental_cars_ui.domain.usecase.*
import com.ankit.rental_cars_ui.presentation.viewmodel.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    // DataStore
    single { SettingsDataStore(androidContext()) }
    
    // Repositories
    single<CarRepository> { CarRepositoryImpl() }
}

val domainModule = module {
    // Use Cases
    single { GetLuxuriousCarsUseCase(get()) }
    single { GetVipCarsUseCase(get()) }
    single { GetUserSettingsUseCase(get()) }
    single { UpdateUserSettingsUseCase(get()) }
}

val viewModelModule = module {
    // ViewModels
    viewModel { 
        HomeViewModel(
            getLuxuriousCarsUseCase = get(),
            getVipCarsUseCase = get()
        )
    }
    viewModel { 
        SettingsViewModel(
            getUserSettingsUseCase = get(),
            updateUserSettingsUseCase = get()
        )
    }
}

// Combine all modules
val appModules = listOf(dataModule, domainModule, viewModelModule) 