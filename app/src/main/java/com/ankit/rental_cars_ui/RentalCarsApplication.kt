package com.ankit.rental_cars_ui

import android.app.Application
import com.ankit.rental_cars_ui.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class RentalCarsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger(Level.ERROR) // Only log errors in production
            androidContext(this@RentalCarsApplication)
            modules(appModules)
        }
    }
} 