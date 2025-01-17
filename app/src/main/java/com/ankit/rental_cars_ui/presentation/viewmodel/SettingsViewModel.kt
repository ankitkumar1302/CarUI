package com.ankit.rental_cars_ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankit.rental_cars_ui.data.datastore.SettingsDataStore

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    var state by mutableStateOf(SettingsState())
        private set

    init {
        observeSettings()
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ToggleDarkMode -> {
                viewModelScope.launch {
                    settingsDataStore.setDarkMode(event.enabled)
                }
            }
            is SettingsEvent.ToggleNotifications -> {
                viewModelScope.launch {
                    settingsDataStore.setNotifications(event.enabled)
                }
            }
        }
    }

    private fun observeSettings() {
        settingsDataStore.isDarkMode.onEach { isDarkMode ->
            state = state.copy(isDarkMode = isDarkMode)
        }.launchIn(viewModelScope)

        settingsDataStore.isNotificationsEnabled.onEach { isNotificationsEnabled ->
            state = state.copy(isNotificationsEnabled = isNotificationsEnabled)
        }.launchIn(viewModelScope)
    }
}

data class SettingsState(
    val isDarkMode: Boolean = false,
    val isNotificationsEnabled: Boolean = true
)

sealed class SettingsEvent {
    data class ToggleDarkMode(val enabled: Boolean) : SettingsEvent()
    data class ToggleNotifications(val enabled: Boolean) : SettingsEvent()
} 