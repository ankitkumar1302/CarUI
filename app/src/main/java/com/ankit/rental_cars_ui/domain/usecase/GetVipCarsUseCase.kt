package com.ankit.rental_cars_ui.domain.usecase

import com.ankit.rental_cars_ui.domain.model.Car
import com.ankit.rental_cars_ui.domain.repository.CarRepository
import kotlinx.coroutines.flow.Flow

class GetVipCarsUseCase(
    private val carRepository: CarRepository
) {
    operator fun invoke(): Flow<List<Car>> = carRepository.getVipCars()
} 