package com.ankit.rental_cars_ui.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ankit.rental_cars_ui.domain.model.Car
import kotlinx.coroutines.delay

@Composable
fun CarList(
    cars: List<Car>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    isFirstLaunch: Boolean,
    onCarClick: (Car) -> Unit,
    listState: LazyListState = rememberLazyListState()
) {
    // Initialize with minimum of list size or 2 items
    var loadedItems by remember(cars) { 
        mutableStateOf(minOf(2, cars.size))
    }

    // Calculate loading progress
    val loadingProgress by remember(loadedItems, cars) {
        derivedStateOf {
            if (cars.isEmpty()) 0f else loadedItems.toFloat() / cars.size
        }
    }

    // Check if we need to load more items
    LaunchedEffect(listState.firstVisibleItemIndex, cars) {
        if (listState.firstVisibleItemIndex > loadedItems - 3 && loadedItems < cars.size) {
            delay(300) // Reduced delay for smoother loading
            loadedItems = minOf(loadedItems + 2, cars.size)
        }
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(count = loadedItems) { index ->
                if (index < cars.size) {  // Add safety check
                    CarItem(
                        car = cars[index],
                        onClick = { onCarClick(cars[index]) },
                        isFirstLaunch = isFirstLaunch
                    )
                }
            }

            // Show loading indicator if there are more items to load
            if (loadedItems < cars.size) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }

        // Top progress indicator
        LinearProgressIndicator(
            progress = loadingProgress,
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .clip(RoundedCornerShape(1.dp))
                .align(Alignment.TopCenter),
            color = Color.White,
            trackColor = Color.White.copy(alpha = 0.2f)
        )
    }
}

@Composable
private fun CarItem(
    car: Car,
    onClick: () -> Unit,
    isFirstLaunch: Boolean
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300) // Small delay before animation
        isVisible = true
    }

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .graphicsLayer {
                alpha = if (isVisible) 1f else 0f
                translationY = if (isVisible) 0f else 50f
            }
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Car Image
            Image(
                painter = painterResource(id = car.image),
                contentDescription = car.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(24.dp))
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Logo and Rating
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2A2A2A)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = car.logo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(8.dp)
                        )
                    }
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFC107).copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = car.recommendationRate.toString(),
                                fontSize = 14.sp,
                                color = Color(0xFFFFC107),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Car Details
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = car.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "${car.rentalDays}+ Day Rental",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                    Text(
                        text = "$${car.price}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
} 