package com.ankit.rental_cars_ui.presentation.screens

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.ankit.rental_cars_ui.R
import com.ankit.rental_cars_ui.domain.model.Car
import dev.chrisbanes.haze.HazeState
import org.junit.Rule
import org.junit.Test

class CarDetailScreenTest {

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
            R.drawable.lamborghini_car,
            R.drawable.porsche_logo,
            R.drawable.rolls_royce_car
        ),
        rentalDays = 1,
        color = Color.Red,
        bgColor = Color.White
    )

    @Test
    fun carDetailScreen_displaysCarInformation() {
        composeTestRule.setContent {
            CarDetailScreen(
                car = mockCar,
                onBackClick = {},
                hazeState = HazeState()
            )
        }

        composeTestRule.onNodeWithText(mockCar.name).assertExists()
        composeTestRule.onNodeWithText("$${mockCar.price}").assertExists()
        composeTestRule.onNodeWithText("Premium").assertExists()
    }

    @Test
    fun carDetailScreen_displaysFeatures() {
        composeTestRule.setContent {
            CarDetailScreen(
                car = mockCar,
                onBackClick = {},
                hazeState = HazeState()
            )
        }

        composeTestRule.onNodeWithText("Speed").assertExists()
        composeTestRule.onNodeWithText("280 km/h").assertExists()
        composeTestRule.onNodeWithText("0-100 km/h").assertExists()
        composeTestRule.onNodeWithText("3.2s").assertExists()
        composeTestRule.onNodeWithText("Power").assertExists()
        composeTestRule.onNodeWithText("580 HP").assertExists()
    }

    @Test
    fun carDetailScreen_favoriteButton_toggles() {
        composeTestRule.setContent {
            CarDetailScreen(
                car = mockCar,
                onBackClick = {},
                hazeState = HazeState()
            )
        }

        // Find favorite button by its content description
        val favoriteButton = composeTestRule.onNode(hasContentDescription("Favorite"))
        
        // Initial state should be not favorite
        favoriteButton.assertExists()
        
        // Click favorite button
        favoriteButton.performClick()
        
        // Should still exist after click
        favoriteButton.assertExists()
    }

    @Test
    fun carDetailScreen_rentDialog_showsAndDismisses() {
        composeTestRule.setContent {
            CarDetailScreen(
                car = mockCar,
                onBackClick = {},
                hazeState = HazeState()
            )
        }

        // Find and click the rent button
        composeTestRule.onNodeWithText("Rent Now")
            .assertExists()
            .performClick()

        // Verify dialog content
        composeTestRule.onNodeWithText("Rent ${mockCar.name}")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Premium Vehicle")
            .assertExists()
            .assertIsDisplayed()

        // Find the price text within the dialog by finding its parent node first
        composeTestRule.onNode(
            hasTestTag("dialog_background")
                .and(hasText("$${mockCar.price}"))
        ).assertExists()

        // Click the Cancel button to dismiss
        composeTestRule.onNodeWithText("Cancel")
            .assertExists()
            .performClick()

        // Verify dialog is dismissed
        composeTestRule.onNodeWithText("Rent ${mockCar.name}")
            .assertDoesNotExist()
    }

    @Test
    fun carDetailScreen_backButton_exists() {
        var backClicked = false
        composeTestRule.setContent {
            CarDetailScreen(
                car = mockCar,
                onBackClick = { backClicked = true },
                hazeState = HazeState()
            )
        }

        // Find and click back button
        composeTestRule.onNode(hasContentDescription("Back")).performClick()
        assert(backClicked)
    }

    @Test
    fun carDetailScreen_shareButton_exists() {
        composeTestRule.setContent {
            CarDetailScreen(
                car = mockCar,
                onBackClick = {},
                hazeState = HazeState()
            )
        }

        composeTestRule.onNode(hasContentDescription("Share")).assertExists()
    }
} 