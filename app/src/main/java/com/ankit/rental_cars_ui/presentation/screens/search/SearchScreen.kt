package com.ankit.rental_cars_ui.presentation.screens.search

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ankit.rental_cars_ui.domain.model.Car
import com.ankit.rental_cars_ui.domain.model.luxuriousCars
import com.ankit.rental_cars_ui.domain.model.vipCars
import com.ankit.rental_cars_ui.presentation.components.TopBar
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    hazeState: HazeState,
    onCarClick: (Car) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var showFilters by remember { mutableStateOf(false) }
    var selectedPriceRange by remember { mutableStateOf(0f..2000f) }
    var selectedCategory by remember { mutableStateOf("All") }
    var sortBy by remember { mutableStateOf("Recommended") }
    
    val allCars = remember { luxuriousCars + vipCars }
    
    val filteredCars = remember(searchQuery, selectedPriceRange, selectedCategory, sortBy) {
        allCars.filter { car ->
            val matchesSearch = car.name.contains(searchQuery, ignoreCase = true)
            val matchesPrice = car.price in selectedPriceRange.start.toInt()..selectedPriceRange.endInclusive.toInt()
            val matchesCategory = selectedCategory == "All" || 
                (selectedCategory == "Luxurious" && car in luxuriousCars) ||
                (selectedCategory == "VIP" && car in vipCars)
            
            matchesSearch && matchesPrice && matchesCategory
        }.sortedBy { car ->
            when (sortBy) {
                "Price: Low to High" -> car.price
                "Price: High to Low" -> -car.price
                "Rating" -> -car.recommendationRate.toInt()
                else -> -car.recommendation
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Background gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A1A1A),
                            Color.Black
                        )
                    )
                )
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar
            TopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .hazeChild(
                        hazeState,
                        style = HazeStyle(
                            tint = Color(0xFF1E1E1E).copy(alpha = 0.7f),
                            blurRadius = 20.dp
                        )
                    ),
                title = "Search Cars",
                onBackClick = { navController.popBackStack() }
            )

            // Search Bar and Filters
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Search TextField
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search for cars...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null,
                            tint = Color(0xFFFFD700)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = "Clear",
                                    tint = Color.Gray
                                )
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFFD700),
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color(0xFFFFD700),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                // Filter Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${filteredCars.size} cars found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    OutlinedButton(
                        onClick = { showFilters = !showFilters },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFFFD700)
                        ),
                        border = BorderStroke(1.dp, Color(0xFFFFD700))
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FilterList,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Filters")
                    }
                }

                // Filters Section
                AnimatedVisibility(
                    visible = showFilters,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2A2A2A)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Category Filter
                            Text(
                                text = "Category",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf("All", "Luxurious", "VIP").forEach { category ->
                                    FilterChip(
                                        selected = selectedCategory == category,
                                        onClick = { selectedCategory = category },
                                        label = { Text(category) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = Color(0xFFFFD700),
                                            selectedLabelColor = Color.Black,
                                            containerColor = Color(0xFF3A3A3A),
                                            labelColor = Color.White
                                        )
                                    )
                                }
                            }

                            // Price Range
                            Text(
                                text = "Price Range",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White
                            )
                            Column {
                                RangeSlider(
                                    value = selectedPriceRange,
                                    onValueChange = { selectedPriceRange = it },
                                    valueRange = 0f..2000f,
                                    colors = SliderDefaults.colors(
                                        thumbColor = Color(0xFFFFD700),
                                        activeTrackColor = Color(0xFFFFD700),
                                        inactiveTrackColor = Color.Gray
                                    )
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "$${selectedPriceRange.start.toInt()}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "$${selectedPriceRange.endInclusive.toInt()}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                            }

                            // Sort By
                            Text(
                                text = "Sort By",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf("Recommended", "Price: Low to High", "Price: High to Low", "Rating").forEach { sort ->
                                    FilterChip(
                                        selected = sortBy == sort,
                                        onClick = { sortBy = sort },
                                        label = { 
                                            Text(
                                                text = sort,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = Color(0xFFFFD700),
                                            selectedLabelColor = Color.Black,
                                            containerColor = Color(0xFF3A3A3A),
                                            labelColor = Color.White
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Search Results
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(filteredCars) { car ->
                    CarSearchItem(
                        car = car,
                        onClick = { onCarClick(car) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CarSearchItem(
    car: Car,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Car Image
            Image(
                painter = painterResource(id = car.image),
                contentDescription = car.name,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            // Car Details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = car.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFFFFD700)
                        )
                        Text(
                            text = "${car.recommendationRate}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Text(
                            text = "•",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                        Text(
                            text = "${car.recommendation}% recommend",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "$${car.price}/day",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700)
                    )

                    Button(
                        onClick = onClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFD700)
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = "View",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}