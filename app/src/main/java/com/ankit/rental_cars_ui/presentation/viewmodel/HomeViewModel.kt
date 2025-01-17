package com.ankit.rental_cars_ui.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ankit.rental_cars_ui.domain.model.Car
import com.ankit.rental_cars_ui.domain.repository.CarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val carRepository: CarRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        getLuxuriousCars()
        getVipCars()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.CategorySelected -> {
                _state.update { it.copy(selectedCategory = event.index) }
            }
            is HomeEvent.CarSelected -> {
                _state.update { it.copy(selectedCar = event.car) }
            }
        }
    }

    private fun getLuxuriousCars() {
        viewModelScope.launch {
            carRepository.getLuxuriousCars().collect { cars ->
                _state.update { it.copy(luxuriousCars = cars) }
            }
        }
    }

    private fun getVipCars() {
        viewModelScope.launch {
            carRepository.getVipCars().collect { cars ->
                _state.update { it.copy(vipCars = cars) }
            }
        }
    }
}

data class HomeState(
    val selectedCategory: Int = 0,
    val selectedCar: Car? = null,
    val luxuriousCars: List<Car> = emptyList(),
    val vipCars: List<Car> = emptyList()
)

sealed class HomeEvent {
    data class CategorySelected(val index: Int) : HomeEvent()
    data class CarSelected(val car: Car?) : HomeEvent()
} 