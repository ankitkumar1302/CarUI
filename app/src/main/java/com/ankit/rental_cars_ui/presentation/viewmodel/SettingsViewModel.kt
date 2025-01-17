package com.ankit.rental_cars_ui.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankit.rental_cars_ui.domain.usecase.GetUserSettingsUseCase
import com.ankit.rental_cars_ui.domain.usecase.UpdateUserSettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getUserSettingsUseCase: GetUserSettingsUseCase,
    private val updateUserSettingsUseCase: UpdateUserSettingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init {
        observeSettings()
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ToggleDarkMode -> {
                viewModelScope.launch {
                    updateUserSettingsUseCase(!state.value.isDarkMode)
                }
            }
        }
    }

    private fun observeSettings() {
        viewModelScope.launch {
            getUserSettingsUseCase().collect { isDarkMode ->
                _state.update { it.copy(isDarkMode = isDarkMode) }
            }
        }
    }
}

data class SettingsState(
    val isDarkMode: Boolean = false
)

sealed class SettingsEvent {
    object ToggleDarkMode : SettingsEvent()
} 