package com.ankit.rental_cars_ui.domain.repository

import com.ankit.rental_cars_ui.domain.model.Car
import kotlinx.coroutines.flow.Flow

interface CarRepository {
    fun getLuxuriousCars(): Flow<List<Car>>
    fun getVipCars(): Flow<List<Car>>
    fun getCarById(id: String): Flow<Car?>
    suspend fun updateCarDetails(car: Car)
} 