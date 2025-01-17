package com.ankit.rental_cars_ui.domain.usecase

import com.ankit.rental_cars_ui.domain.model.Car
import com.ankit.rental_cars_ui.domain.repository.CarRepository
import kotlinx.coroutines.flow.Flow

class GetVipCarsUseCase(
    private val repository: CarRepository
) {
    operator fun invoke(): Flow<List<Car>> {
        return repository.getVipCars()
    }
} 