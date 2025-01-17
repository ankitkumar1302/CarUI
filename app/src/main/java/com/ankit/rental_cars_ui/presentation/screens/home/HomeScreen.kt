package com.ankit.rental_cars_ui.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.ankit.rental_cars_ui.presentation.components.CarList
import com.ankit.rental_cars_ui.presentation.components.Pager
import com.ankit.rental_cars_ui.presentation.components.TopBar
import com.ankit.rental_cars_ui.presentation.viewmodel.HomeEvent
import com.ankit.rental_cars_ui.presentation.viewmodel.HomeState
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import androidx.navigation.NavController
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    hazeState: HazeState? = null,
    scrollBehavior: TopAppBarScrollBehavior,
    isFirstLaunch: Boolean,
    navController: NavController
) {
    // Remember the gradient brushes to prevent recreation on each recomposition
    val backgroundGradient = remember {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF1A1A1A),
                Color.Black
            )
        )
    }

    val topBarGradient = remember {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF1E1E1E),
                Color.Transparent
            ),
            startY = 0f,
            endY = Float.POSITIVE_INFINITY
        )
    }

    // Remember the haze styles to prevent recreation
    val hazeStyle = remember {
        HazeStyle(
            tint = Color(0xFF1E1E1E).copy(alpha = 0.5f),
            blurRadius = 16.dp
        )
    }

    val pagerHazeStyle = remember {
        HazeStyle(
            tint = Color(0xFF1E1E1E).copy(alpha = 0.3f),
            blurRadius = 12.dp
        )
    }

    val listHazeStyle = remember {
        HazeStyle(
            blurRadius = 13.dp,
            tint = Color(0xFF1A1A1A).copy(alpha = 0.8f)
        )
    }

    // Derive the current car list based on selected category
    val currentCarList = remember(state.selectedCategory, state.luxuriousCars, state.vipCars) {
        derivedStateOf {
            if (state.selectedCategory == 0) state.luxuriousCars else state.vipCars
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
        )

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            containerColor = Color.Transparent,
            topBar = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(topBarGradient)
                        .hazeChild(
                            state = hazeState ?: HazeState(),
                            style = hazeStyle
                        ),
                    color = Color.Transparent
                ) {
                    Column {
                        TopBar(
                            scrollBehavior = scrollBehavior
                        )
                        if (!state.isLoading) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .hazeChild(
                                        state = hazeState ?: HazeState(),
                                        style = pagerHazeStyle
                                    ),
                                color = Color.Transparent
                            ) {
                                Pager(
                                    selectedCategory = state.selectedCategory,
                                    onCategorySelected = { index ->
                                        onEvent(HomeEvent.CategorySelected(index))
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                } else if (state.error != null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = state.error,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Button(
                                onClick = { onEvent(HomeEvent.RetryLoading) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF2196F3)
                                )
                            ) {
                                Text("Retry")
                            }
                        }
                    }
                } else {
                    CarList(
                        cars = currentCarList.value,
                        modifier = Modifier
                            .fillMaxSize()
                            .haze(
                                state = hazeState ?: HazeState(),
                                style = listHazeStyle
                            ),
                        paddingValues = innerPadding,
                        isFirstLaunch = isFirstLaunch,
                        onCarClick = { car ->
                            onEvent(HomeEvent.CarSelected(car))
                            navController.navigate("car_detail")
                        }
                    )

                    // Show loading indicator for more items
                    if (state.isLoadingMore) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .align(Alignment.BottomCenter)
                        ) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
} 