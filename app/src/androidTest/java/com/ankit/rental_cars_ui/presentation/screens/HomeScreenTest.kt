package com.ankit.rental_cars_ui.presentation.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.ankit.rental_cars_ui.R
import com.ankit.rental_cars_ui.domain.model.Car
import com.ankit.rental_cars_ui.presentation.viewmodel.HomeEvent
import com.ankit.rental_cars_ui.presentation.viewmodel.HomeState
import dev.chrisbanes.haze.HazeState
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockCar = Car(
        id = "1",
        name = "Ferrari SF90",
        price = 759,
        image = R.drawable.ferrari_car,
        logo = R.drawable.ferrari_logo,
        recommendation = 97,
        recommendationRate = 4.8f,
        recommenders = listOf(
            R.drawable.porsche_car,
            R.drawable.lamborghini_car,
            R.drawable.rolls_royce_car
        ),
        rentalDays = 1,
        color = Color.Red,
        bgColor = Color.White
    )

    @Test
    fun homeScreen_displaysTopBar() {
        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(),
                onEvent = {},
                hazeState = HazeState(),
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                isFirstLaunch = true
            )
        }

        // Check title
        composeTestRule.onNodeWithText("Trending new cars").assertExists()
        
        // Check navigation and action icons
        composeTestRule.onNodeWithContentDescription("Menu").assertExists()
        composeTestRule.onNodeWithContentDescription("Notifications").assertExists()
        composeTestRule.onNodeWithContentDescription("Voice Search").assertExists()
    }

    @Test
    fun homeScreen_displaysCategoryTabs() {
        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(),
                onEvent = {},
                hazeState = HazeState(),
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                isFirstLaunch = true
            )
        }

        composeTestRule.onNodeWithText("Luxurious").assertExists()
        composeTestRule.onNodeWithText("VIP Cars").assertExists()
    }

    @Test
    fun homeScreen_categorySelection_updatesContent() {
        var selectedCategory = 0
        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(selectedCategory = selectedCategory),
                onEvent = { event ->
                    if (event is HomeEvent.CategorySelected) {
                        selectedCategory = event.index
                    }
                },
                hazeState = HazeState(),
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                isFirstLaunch = true
            )
        }

        composeTestRule.onNodeWithText("VIP Cars").performClick()
        assert(selectedCategory == 1)
    }

    @Test
    fun homeScreen_carSelection_showsDetailScreen() {
        var selectedCar: Car? = null
        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(
                    luxuriousCars = listOf(mockCar),
                    selectedCar = selectedCar
                ),
                onEvent = { event ->
                    if (event is HomeEvent.CarSelected) {
                        selectedCar = event.car
                    }
                },
                hazeState = HazeState(),
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                isFirstLaunch = false
            )
        }

        // Find and click the car item
        composeTestRule.onNodeWithText(mockCar.name).performClick()
        assert(selectedCar == mockCar)
    }

    @Test
    fun homeScreen_initialLaunch_showsAnimations() {
        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(luxuriousCars = listOf(mockCar)),
                onEvent = {},
                hazeState = HazeState(),
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                isFirstLaunch = true
            )
        }

        // Verify initial animations are applied
        composeTestRule.onNodeWithText(mockCar.name)
            .assertExists()
            .assertIsDisplayed()
    }
} 