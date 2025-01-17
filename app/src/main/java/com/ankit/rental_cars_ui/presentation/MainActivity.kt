package com.ankit.rental_cars_ui.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ankit.rental_cars_ui.presentation.components.BottomBar
import com.ankit.rental_cars_ui.presentation.screens.home.HomeScreen
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

                val homeState by homeViewModel.state.collectAsState()
                var isFirstLaunch by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    isFirstLaunch = false
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    HomeScreen(
                        state = homeState,
                        onEvent = homeViewModel::onEvent,
                        hazeState = hazeState,
                        scrollBehavior = scrollBehavior,
                        isFirstLaunch = isFirstLaunch
                    )

                    if (homeState.selectedCar == null) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .navigationBarsPadding()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            BottomBar(
                                modifier = Modifier.fillMaxWidth(),
                                hazeState = hazeState
                            )
                        }
                    }
                }
            }
        }
    }
} 