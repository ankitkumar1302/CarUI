package com.ankit.rental_cars_ui.domain.model

import java.time.LocalDate
import java.util.UUID

data class Booking(
    val id: String = UUID.randomUUID().toString(),
    val carId: String,
    val carName: String,
    val carImage: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val totalDays: Int,
    val pricePerDay: Int,
    val totalPrice: Int,
    val status: BookingStatus,
    val bookingDate: LocalDate = LocalDate.now(),
    val pickupLocation: String,
    val dropoffLocation: String
)

enum class BookingStatus {
    PENDING,
    CONFIRMED,
    ACTIVE,
    COMPLETED,
    CANCELLED
}