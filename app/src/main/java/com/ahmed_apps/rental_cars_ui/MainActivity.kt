package com.ahmed_apps.rental_cars_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ahmed_apps.rental_cars_ui.data.SettingsDataStore

import com.ahmed_apps.rental_cars_ui.screens.SettingsScreen
import com.ahmed_apps.rental_cars_ui.ui.theme.RentalCarsUITheme
import com.ankit.rental_cars_ui.BottomBar
import com.ankit.rental_cars_ui.Car
import com.ankit.rental_cars_ui.CarDetailScreen
import com.ankit.rental_cars_ui.HomeScreen
import com.ankit.rental_cars_ui.Pager
import com.ankit.rental_cars_ui.TopBar
import com.ankit.rental_cars_ui.screens.AccountScreen
import com.ankit.rental_cars_ui.screens.AnalyticsScreen
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val settingsDataStore = SettingsDataStore(this)
        
        setContent {
            val isDarkMode by settingsDataStore.isDarkMode.collectAsState(initial = false)
            
            RentalCarsUITheme(darkTheme = isDarkMode) {
                var selectedCar by remember { mutableStateOf<Car?>(null) }
                var currentScreen by remember { mutableIntStateOf(0) }
                var isFirstLaunch by remember { mutableStateOf(true) }
                val hazeState = remember { HazeState() }
                val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                    state = rememberTopAppBarState()
                )
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    delay(500)
                    isFirstLaunch = false
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    AnimatedContent(
                        targetState = Pair(currentScreen, selectedCar),
                        transitionSpec = {
                            val (oldScreen, _) = initialState
                            val (newScreen, _) = targetState
                            val direction = if (newScreen > oldScreen) 1 else -1

                            slideInHorizontally(
                                animationSpec = tween(200, easing = FastOutSlowInEasing)
                            ) { width -> direction * width } + fadeIn(
                                animationSpec = tween(150)
                            ) with slideOutHorizontally(
                                animationSpec = tween(200, easing = FastOutSlowInEasing)
                            ) { width -> -direction * width } + fadeOut(
                                animationSpec = tween(150)
                            )
                        }
                    ) { (screen, car) ->
                        when {
                            car != null -> {
                                CarDetailScreen(
                                    car = car,
                                    onBackClick = {
                                        coroutineScope.launch {
                                            selectedCar = null
                                        }
                                    }
                                )
                            }

                            screen == 0 -> {
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
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                )
                                            }
                                        }
                                    }
                                ) { innerPadding ->
                                    HomeScreen(
                                        modifier = Modifier.fillMaxSize(),
                                        hazeState = hazeState,
                                        paddingValues = innerPadding,
                                        isFirstLaunch = isFirstLaunch,
                                        onCarClick = { car ->
                                            coroutineScope.launch {
                                                selectedCar = car
                                            }
                                        }
                                    )
                                }
                            }

                            screen == 1 -> {
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                    color = MaterialTheme.colorScheme.background
                                ) {
                                    AccountScreen(hazeState = hazeState)
                                }
                            }

                            screen == 2 -> {
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                    color = MaterialTheme.colorScheme.background
                                ) {
                                    AnalyticsScreen(hazeState = hazeState)
                                }
                            }

                            screen == 3 -> {
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                    color = MaterialTheme.colorScheme.background
                                ) {
                                    SettingsScreen(
                                        hazeState = hazeState,
                                        isDarkMode = isDarkMode,
                                        onDarkModeChange = { enabled ->
                                            coroutineScope.launch {
                                                settingsDataStore.setDarkMode(enabled)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }

                    AnimatedVisibility(
                        visible = selectedCar == null,
                        enter = slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(300, easing = FastOutSlowInEasing)
                        ) + fadeIn(tween(200)),
                        exit = slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(200, easing = FastOutSlowInEasing)
                        ) + fadeOut(tween(150)),
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        BottomBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 26.dp)
                                .padding(bottom = 26.dp)
                                .hazeChild(
                                    state = hazeState,
                                    shape = RoundedCornerShape(26.dp)
                                ),
                            onNavigate = { index ->
                                currentScreen = index
                            }
                        )
                    }
                }
            }
        }
    }
} 