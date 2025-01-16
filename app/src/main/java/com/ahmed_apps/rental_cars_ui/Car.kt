package com.ahmed_apps.rental_cars_ui

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class Car(
    val name: String,
    val brand: String,
    @DrawableRes val image: Int,
    val color: Color,
    @DrawableRes val logo: Int,
    val recommendation: Int,
    val recommendationRate: Float,
    val rentalDays: Int,
    val price: Int,
    val recommenders: List<Int>,
    val bgColor: Color,
    val seats: Int = 4,
    val engineType: String = "Automatic",
    val rating: Float = 4.5f
)

val luxuriousCars = listOf(
    Car(
        name = "Ferrari SF90 Stradale",
        brand = "Ferrari",
        image = R.drawable.ferrari_car,
        color = Color.Red,
        logo = R.drawable.ferrari_logo,
        recommendation = 97,
        recommendationRate = 4.8f,
        rentalDays = 7,
        price = 759,
        recommenders = listOf(
            R.drawable.m_2, R.drawable.w_1, R.drawable.w_2
        ),
        bgColor = Color(0xFFFFE1B3)
    ),
    Car(
        name = "Rolls-Royce Phantom",
        brand = "Rolls-Royce",
        image = R.drawable.rolls_royce_car,
        color = Color.Black,
        logo = R.drawable.rolls_royce_logo,
        recommendation = 98,
        recommendationRate = 4.7f,
        rentalDays = 10,
        price = 799,
        recommenders = listOf(
            R.drawable.m_1, R.drawable.w_2, R.drawable.m_3
        ),
        bgColor = Color(0xFFd5dcf6)
    ),
    Car(
        name = "Porsche 911 Turbo S",
        brand = "Porsche",
        image = R.drawable.porsche_car,
        color = Color.Yellow,
        logo = R.drawable.porsche_logo,
        recommendation = 99,
        recommendationRate = 4.8f,
        rentalDays = 6,
        price = 689,
        recommenders = listOf(
            R.drawable.m_3, R.drawable.w_1, R.drawable.m_1
        ),
        bgColor = Color(0xFFFFE1B3)
    )
)

val vipCars = listOf(
    Car(
        name = "Bugatti Chiron",
        brand = "Bugatti",
        image = R.drawable.ferrari_car,
        color = Color.Blue,
        logo = R.drawable.ferrari_logo,
        recommendation = 99,
        recommendationRate = 5.0f,
        rentalDays = 3,
        price = 1299,
        recommenders = listOf(
            R.drawable.m_1, R.drawable.w_1, R.drawable.m_2
        ),
        bgColor = Color(0xFFFFE1B3)
    ),
    Car(
        name = "McLaren P1",
        brand = "McLaren",
        image = R.drawable.porsche_car,
        color = Color.Yellow,
        logo = R.drawable.porsche_logo,
        recommendation = 98,
        recommendationRate = 4.9f,
        rentalDays = 4,
        price = 1199,
        recommenders = listOf(
            R.drawable.m_2, R.drawable.w_2, R.drawable.m_3
        ),
        bgColor = Color(0xFFd5dcf6)
    )
) 