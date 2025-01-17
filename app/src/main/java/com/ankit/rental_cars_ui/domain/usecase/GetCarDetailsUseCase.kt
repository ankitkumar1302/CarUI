package com.ankit.rental_cars_ui.domain.usecase

import com.ankit.rental_cars_ui.domain.model.Car
import com.ankit.rental_cars_ui.domain.repository.CarRepository
import kotlinx.coroutines.flow.Flow

class GetCarDetailsUseCase(
    private val repository: CarRepository
) {
    operator fun invoke(id: String): Flow<Car?> {
        return repository.getCarById(id)
    }
} 