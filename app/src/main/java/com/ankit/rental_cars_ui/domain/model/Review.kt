package com.ankit.rental_cars_ui.domain.model

import java.time.LocalDate
import java.util.UUID

data class Review(
    val id: String = UUID.randomUUID().toString(),
    val carId: String,
    val userId: String,
    val userName: String,
    val userImage: Int,
    val rating: Float,
    val comment: String,
    val date: LocalDate = LocalDate.now(),
    val helpfulCount: Int = 0,
    val verified: Boolean = false
)