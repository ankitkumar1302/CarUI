package com.ankit.rental_cars_ui.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.ankit.rental_cars_ui.presentation.components.CarList
import com.ankit.rental_cars_ui.presentation.components.Pager
import com.ankit.rental_cars_ui.presentation.components.TopBar
import com.ankit.rental_cars_ui.presentation.viewmodel.HomeEvent
import com.ankit.rental_cars_ui.presentation.viewmodel.HomeState
import com.ankit.rental_cars_ui.ui.theme.Blur
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    hazeState: HazeState,
    scrollBehavior: TopAppBarScrollBehavior,
    isFirstLaunch: Boolean
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = Color.Transparent,
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .hazeChild(
                        state = hazeState,
                        style = HazeStyle(
                            tint = Color.Black.copy(alpha = 0.4f),
                            blurRadius = 20.dp
                        )
                    ),
                color = Color.Transparent
            ) {
                Column {
                    TopBar(
                        scrollBehavior = scrollBehavior
                    )
                    Pager(
                        selectedCategory = state.selectedCategory,
                        onCategorySelected = { index ->
                            onEvent(HomeEvent.CategorySelected(index))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            CarList(
                cars = if (state.selectedCategory == 0) state.luxuriousCars else state.vipCars,
                modifier = Modifier
                    .fillMaxSize()
                    .haze(
                        state = hazeState,
                        style = HazeStyle(
                            blurRadius = 13.dp,
                            tint = Blur
                        )
                    ),
                paddingValues = innerPadding,
                isFirstLaunch = isFirstLaunch,
                onCarClick = { car ->
                    onEvent(HomeEvent.CarSelected(car))
                }
            )
        }
    }
} 