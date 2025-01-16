package com.ahmed_apps.rental_cars_ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.PaddingValues
import com.ankit.rental_cars_ui.CarList
import dev.chrisbanes.haze.HazeState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    paddingValues: PaddingValues,
    isFirstLaunch: Boolean = false,
    onCarClick: (Car) -> Unit = {}
) {
    var selectedCategory by remember { mutableIntStateOf(0) }

    CarList(
        modifier = modifier,
        paddingValues = paddingValues,
        selectedCategory = selectedCategory,
        isFirstLaunch = isFirstLaunch,
        onCarClick = onCarClick
    )
} 