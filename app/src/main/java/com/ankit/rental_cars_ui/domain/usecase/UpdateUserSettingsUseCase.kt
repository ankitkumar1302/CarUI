package com.ankit.rental_cars_ui.domain.usecase

import com.ankit.rental_cars_ui.data.datastore.SettingsDataStore

class UpdateUserSettingsUseCase(
    private val settingsDataStore: SettingsDataStore
) {
    suspend operator fun invoke(isDarkMode: Boolean) {
        settingsDataStore.updateDarkMode(isDarkMode)
    }
} 