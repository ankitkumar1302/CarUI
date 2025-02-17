package com.ankit.rental_cars_ui.presentation

import CarDetailScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ankit.rental_cars_ui.presentation.components.BottomBar
import com.ankit.rental_cars_ui.presentation.screens.home.HomeScreen

import com.ankit.rental_cars_ui.presentation.screens.account.AccountScreen
import com.ankit.rental_cars_ui.presentation.screens.analytic.AnalyticsScreen
import com.ankit.rental_cars_ui.presentation.screens.setting.SettingsScreen

import com.ankit.rental_cars_ui.presentation.viewmodel.HomeEvent
import com.ankit.rental_cars_ui.presentation.viewmodel.HomeViewModel
import com.ankit.rental_cars_ui.ui.theme.RentalCarsUITheme
import dev.chrisbanes.haze.HazeState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            RentalCarsUITheme {
                val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
                val hazeState = remember { HazeState() }
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route ?: "home"

                val homeState by homeViewModel.state.collectAsState()
                var isFirstLaunch by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    isFirstLaunch = false
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1E1E1E))
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable("home") {
                            HomeScreen(
                                state = homeState,
                                onEvent = homeViewModel::onEvent,
                                hazeState = hazeState,
                                scrollBehavior = scrollBehavior,
                                isFirstLaunch = isFirstLaunch,
                                navController = navController
                            )
                        }
                        composable("account") {
                            AccountScreen(hazeState = hazeState)
                        }
                        composable("analytics") {
                            AnalyticsScreen(hazeState = hazeState)
                        }
                        composable("settings") {
                            SettingsScreen(hazeState = hazeState)
                        }
                        composable("car_detail") {
                            homeState.selectedCar?.let { car ->
                                CarDetailScreen(
                                    car = car,
                                    hazeState = hazeState,
                                    onBackClick = {
                                        homeViewModel.onEvent(HomeEvent.CarSelected(null))
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    }

                    if (currentRoute != "car_detail") {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color(0xFF1E1E1E)
                                        ),
                                        startY = 0f,
                                        endY = Float.POSITIVE_INFINITY
                                    )
                                )
                        ) {
                            BottomBar(
                                modifier = Modifier.fillMaxWidth(),
                                hazeState = hazeState,
                                currentRoute = currentRoute,
                                isHomeScreen = currentRoute == "home",
                                onNavigate = { index ->
                                    val route = when (index) {
                                        0 -> "home"
                                        1 -> "account"
                                        2 -> "analytics"
                                        3 -> "settings"
                                        else -> "home"
                                    }
                                    navController.navigate(route) {
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
} 