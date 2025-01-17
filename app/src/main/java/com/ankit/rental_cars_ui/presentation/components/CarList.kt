package com.ankit.rental_cars_ui.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ankit.rental_cars_ui.domain.model.Car

@Composable
fun CarList(
    modifier: Modifier = Modifier,
    cars: List<Car>,
    paddingValues: PaddingValues,
    isFirstLaunch: Boolean = false,
    onCarClick: (Car) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = cars,
            key = { it.id }
        ) { car ->
            CarItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                car = car,
                onCarClick = onCarClick
            )
        }
    }
} 