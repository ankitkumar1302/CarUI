package com.ankit.rental_cars_ui.data.repository

import com.ankit.rental_cars_ui.domain.model.Car
import com.ankit.rental_cars_ui.domain.model.luxuriousCars
import com.ankit.rental_cars_ui.domain.model.vipCars
import com.ankit.rental_cars_ui.domain.repository.CarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CarRepositoryImpl : CarRepository {
    override fun getLuxuriousCars(): Flow<List<Car>> = flow {
        emit(luxuriousCars)
    }

    override fun getVipCars(): Flow<List<Car>> = flow {
        emit(vipCars)
    }

    override fun getCarById(id: String): Flow<Car?> = flow {
        val car = (luxuriousCars + vipCars).find { it.id == id }
        emit(car)
    }

    override suspend fun updateCarDetails(car: Car) {
        // Implement if needed for persistence
    }
} 