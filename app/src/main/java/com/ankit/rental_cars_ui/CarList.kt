package com.ankit.rental_cars_ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CarList(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    selectedCategory: Int = 0,
    isFirstLaunch: Boolean = false,
    onCarClick: (Car) -> Unit = {}
) {
    val cars = if (selectedCategory == 0) luxuriousCars else vipCars
    var loadedItems by remember { mutableStateOf(2) } // Start with 2 items
    val coroutineScope = rememberCoroutineScope()
    
    // Progressive loading effect
    LaunchedEffect(selectedCategory) {
        loadedItems = 2 // Reset to initial items when category changes
        if (cars.size > 2) {
            delay(300) // Small delay before loading more
            loadedItems = cars.size // Load remaining items
        }
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            top = paddingValues.calculateTopPadding() + 22.dp,
            bottom = 90.dp
        ),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        itemsIndexed(
            items = cars.take(loadedItems),
            key = { _, car -> car.name }
        ) { index, car ->
            val scale = remember { Animatable(0.95f) }
            
            LaunchedEffect(Unit) {
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = FastOutSlowInEasing
                    )
                )
            }

            CarItem(
                car = car,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                        alpha = scale.value
                    },
                onCarClick = { 
                    coroutineScope.launch {
                        // Quick feedback animation
                        scale.animateTo(0.95f, tween(100))
                        onCarClick(car)
                    }
                }
            )
        }
    }
}





















