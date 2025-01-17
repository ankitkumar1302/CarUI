package com.ankit.rental_cars_ui

import android.app.Application
import com.ankit.rental_cars_ui.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RentalCarsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger()
            androidContext(this@RentalCarsApplication)
            modules(appModule)
        }
    }
} 