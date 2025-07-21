package com.ankit.rental_cars_ui.presentation.screens.booking

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ankit.rental_cars_ui.R
import com.ankit.rental_cars_ui.domain.model.Booking
import com.ankit.rental_cars_ui.domain.model.BookingStatus
import com.ankit.rental_cars_ui.presentation.components.TopBar
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingHistoryScreen(
    navController: NavController,
    hazeState: HazeState
) {
    // Sample booking data - in real app this would come from ViewModel
    val bookings = remember {
        listOf(
            Booking(
                carId = "1",
                carName = "Ferrari SF90 Stradale",
                carImage = R.drawable.ferrari_car,
                startDate = LocalDate.now().minusDays(7),
                endDate = LocalDate.now().minusDays(3),
                totalDays = 4,
                pricePerDay = 759,
                totalPrice = 3036,
                status = BookingStatus.COMPLETED,
                pickupLocation = "Downtown Office",
                dropoffLocation = "Airport Terminal 1"
            ),
            Booking(
                carId = "2",
                carName = "Porsche 911 Turbo S",
                carImage = R.drawable.porsche_car,
                startDate = LocalDate.now().plusDays(2),
                endDate = LocalDate.now().plusDays(5),
                totalDays = 3,
                pricePerDay = 689,
                totalPrice = 2067,
                status = BookingStatus.CONFIRMED,
                pickupLocation = "City Center",
                dropoffLocation = "City Center"
            ),
            Booking(
                carId = "3",
                carName = "Rolls-Royce Phantom",
                carImage = R.drawable.rolls_royce_car,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusDays(1),
                totalDays = 2,
                pricePerDay = 799,
                totalPrice = 1598,
                status = BookingStatus.ACTIVE,
                pickupLocation = "Hotel Lobby",
                dropoffLocation = "Business District"
            )
        )
    }

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("All", "Active", "Upcoming", "Completed")
    
    val filteredBookings = remember(selectedTab) {
        when (selectedTab) {
            1 -> bookings.filter { it.status == BookingStatus.ACTIVE }
            2 -> bookings.filter { it.status == BookingStatus.CONFIRMED || it.status == BookingStatus.PENDING }
            3 -> bookings.filter { it.status == BookingStatus.COMPLETED }
            else -> bookings
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
                title = "Booking History",
                onBackClick = { navController.popBackStack() }
            )

            // Tab Row
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth(),
                edgePadding = 16.dp,
                divider = {},
                containerColor = Color.Transparent,
                contentColor = Color.White
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        selectedContentColor = Color(0xFFFFD700),
                        unselectedContentColor = Color.Gray
                    )
                }
            }

            // Bookings List
            if (filteredBookings.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.DirectionsCar,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = "No bookings found",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
                ) {
                    items(filteredBookings) { booking ->
                        BookingItem(
                            booking = booking,
                            onViewDetails = {
                                // Navigate to booking details
                            },
                            onCancelBooking = {
                                // Handle booking cancellation
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BookingItem(
    booking: Booking,
    onViewDetails: () -> Unit,
    onCancelBooking: () -> Unit
) {
    val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onViewDetails() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Status Badge and Booking Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusBadge(status = booking.status)
                
                Text(
                    text = "Booked on ${booking.bookingDate.format(dateFormatter)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            // Car Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(id = booking.carImage),
                    contentDescription = booking.carName,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = booking.carName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${booking.startDate.format(dateFormatter)} - ${booking.endDate.format(dateFormatter)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Text(
                        text = "${booking.totalDays} days • $${booking.totalPrice}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFFFD700)
                    )
                }
            }

            // Locations
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFFD700)
                    )
                    Text(
                        text = "Pickup: ${booking.pickupLocation}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFFD700)
                    )
                    Text(
                        text = "Drop-off: ${booking.dropoffLocation}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (booking.status) {
                    BookingStatus.ACTIVE -> {
                        OutlinedButton(
                            onClick = onViewDetails,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFFFFD700)
                            ),
                            border = BorderStroke(1.dp, Color(0xFFFFD700))
                        ) {
                            Text("View Details")
                        }
                        Button(
                            onClick = { /* Navigate to extend booking */ },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFD700)
                            )
                        ) {
                            Text("Extend", color = Color.Black)
                        }
                    }
                    BookingStatus.CONFIRMED, BookingStatus.PENDING -> {
                        OutlinedButton(
                            onClick = onCancelBooking,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.Red
                            ),
                            border = BorderStroke(1.dp, Color.Red)
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = onViewDetails,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFD700)
                            )
                        ) {
                            Text("View Details", color = Color.Black)
                        }
                    }
                    BookingStatus.COMPLETED -> {
                        OutlinedButton(
                            onClick = { /* Rebook */ },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFFFFD700)
                            ),
                            border = BorderStroke(1.dp, Color(0xFFFFD700))
                        ) {
                            Text("Book Again")
                        }
                        Button(
                            onClick = { /* Leave review */ },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFD700)
                            )
                        ) {
                            Text("Leave Review", color = Color.Black)
                        }
                    }
                    BookingStatus.CANCELLED -> {
                        Button(
                            onClick = { /* Rebook */ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFD700)
                            )
                        ) {
                            Text("Book Again", color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(status: BookingStatus) {
    val (backgroundColor, textColor, text) = when (status) {
        BookingStatus.PENDING -> Triple(Color(0xFFFFA500).copy(alpha = 0.2f), Color(0xFFFFA500), "Pending")
        BookingStatus.CONFIRMED -> Triple(Color(0xFF00CED1).copy(alpha = 0.2f), Color(0xFF00CED1), "Confirmed")
        BookingStatus.ACTIVE -> Triple(Color(0xFF32CD32).copy(alpha = 0.2f), Color(0xFF32CD32), "Active")
        BookingStatus.COMPLETED -> Triple(Color(0xFF4169E1).copy(alpha = 0.2f), Color(0xFF4169E1), "Completed")
        BookingStatus.CANCELLED -> Triple(Color(0xFFDC143C).copy(alpha = 0.2f), Color(0xFFDC143C), "Cancelled")
    }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}