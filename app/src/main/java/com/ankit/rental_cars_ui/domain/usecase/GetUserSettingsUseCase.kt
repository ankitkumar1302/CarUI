package com.ankit.rental_cars_ui.domain.usecase

import com.ankit.rental_cars_ui.data.datastore.SettingsDataStore
import kotlinx.coroutines.flow.Flow

class GetUserSettingsUseCase(
    private val settingsDataStore: SettingsDataStore
) {
    operator fun invoke(): Flow<Boolean> = settingsDataStore.isDarkMode
} 