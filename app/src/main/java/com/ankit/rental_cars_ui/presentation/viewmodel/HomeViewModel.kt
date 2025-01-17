package com.ankit.rental_cars_ui.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankit.rental_cars_ui.domain.model.Car
import com.ankit.rental_cars_ui.domain.usecase.GetLuxuriousCarsUseCase
import com.ankit.rental_cars_ui.domain.usecase.GetVipCarsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope

class HomeViewModel(
    private val getLuxuriousCarsUseCase: GetLuxuriousCarsUseCase,
    private val getVipCarsUseCase: GetVipCarsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private var luxuriousCarsJob: Job? = null
    private var vipCarsJob: Job? = null

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            // Load initial category first (Luxurious cars)
            supervisorScope {
                val luxuriousDeferred = async { getLuxuriousCarsUseCase().first() }
                try {
                    val luxuriousCars = luxuriousDeferred.await()
                    _state.update { it.copy(
                        luxuriousCars = luxuriousCars,
                        isLoading = false,
                        error = null
                    ) }
                    
                    // Load VIP cars after initial category is loaded
                    getVipCars()
                } catch (e: Exception) {
                    _state.update { it.copy(
                        error = e.message,
                        isLoading = false
                    ) }
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.CategorySelected -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    // Cancel ongoing job of the non-selected category
                    if (event.index == 0) {
                        vipCarsJob?.cancelAndJoin()
                        getLuxuriousCars()
                    } else {
                        luxuriousCarsJob?.cancelAndJoin()
                        getVipCars()
                    }
                    _state.update { it.copy(
                        selectedCategory = event.index,
                        isLoading = false
                    ) }
                }
            }
            is HomeEvent.CarSelected -> {
                _state.update { it.copy(selectedCar = event.car) }
            }
            is HomeEvent.RetryLoading -> {
                loadInitialData()
            }
        }
    }

    private fun getLuxuriousCars() {
        luxuriousCarsJob?.cancel()
        luxuriousCarsJob = viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true) }
            getLuxuriousCarsUseCase()
                .catch { error ->
                    _state.update { it.copy(
                        error = error.message,
                        isLoadingMore = false
                    ) }
                }
                .collect { cars ->
                    _state.update { it.copy(
                        luxuriousCars = cars,
                        error = null,
                        isLoadingMore = false
                    ) }
                }
        }
    }

    private fun getVipCars() {
        vipCarsJob?.cancel()
        vipCarsJob = viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true) }
            getVipCarsUseCase()
                .catch { error ->
                    _state.update { it.copy(
                        error = error.message,
                        isLoadingMore = false
                    ) }
                }
                .collect { cars ->
                    _state.update { it.copy(
                        vipCars = cars,
                        error = null,
                        isLoadingMore = false
                    ) }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        luxuriousCarsJob?.cancel()
        vipCarsJob?.cancel()
    }
}

data class HomeState(
    val selectedCategory: Int = 0,
    val selectedCar: Car? = null,
    val luxuriousCars: List<Car> = emptyList(),
    val vipCars: List<Car> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false
)

sealed class HomeEvent {
    data class CategorySelected(val index: Int) : HomeEvent()
    data class CarSelected(val car: Car?) : HomeEvent()
    object RetryLoading : HomeEvent()
} 