package com.ankit.rental_cars_ui.presentation.screens.booking

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ankit.rental_cars_ui.domain.model.Car
import com.ankit.rental_cars_ui.presentation.components.TopBar
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    car: Car,
    navController: NavController,
    hazeState: HazeState
) {
    var selectedStartDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedEndDate by remember { mutableStateOf(LocalDate.now().plusDays(1)) }
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    var pickupLocation by remember { mutableStateOf("") }
    var dropoffLocation by remember { mutableStateOf("") }
    var addInsurance by remember { mutableStateOf(false) }
    var addGPS by remember { mutableStateOf(false) }
    var addChildSeat by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    val totalDays = ChronoUnit.DAYS.between(selectedStartDate, selectedEndDate).toInt() + 1
    val basePrice = car.price * totalDays
    val insurancePrice = if (addInsurance) 50 * totalDays else 0
    val gpsPrice = if (addGPS) 15 * totalDays else 0
    val childSeatPrice = if (addChildSeat) 20 * totalDays else 0
    val totalPrice = basePrice + insurancePrice + gpsPrice + childSeatPrice

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
            modifier = Modifier
                .fillMaxSize()
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
                title = "Book ${car.name}",
                onBackClick = { navController.popBackStack() }
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Car Summary Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2A2A2A)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = car.image),
                            contentDescription = car.name,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = car.name,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "$${car.price}/day",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFFFD700)
                            )
                        }
                    }
                }

                // Date Selection
                Text(
                    text = "Rental Period",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Start Date
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showStartDatePicker = true },
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2A2A2A)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Start Date",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                                Text(
                                    text = selectedStartDate.format(dateFormatter),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White
                                )
                            }
                            Icon(
                                imageVector = Icons.Rounded.DateRange,
                                contentDescription = null,
                                tint = Color(0xFFFFD700)
                            )
                        }
                    }

                    // End Date
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showEndDatePicker = true },
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2A2A2A)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "End Date",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                                Text(
                                    text = selectedEndDate.format(dateFormatter),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White
                                )
                            }
                            Icon(
                                imageVector = Icons.Rounded.DateRange,
                                contentDescription = null,
                                tint = Color(0xFFFFD700)
                            )
                        }
                    }
                }

                // Location Selection
                Text(
                    text = "Locations",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                OutlinedTextField(
                    value = pickupLocation,
                    onValueChange = { pickupLocation = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Pickup Location") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = null,
                            tint = Color(0xFFFFD700)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFFD700),
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color(0xFFFFD700),
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = Color(0xFFFFD700),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = dropoffLocation,
                    onValueChange = { dropoffLocation = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Drop-off Location") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = null,
                            tint = Color(0xFFFFD700)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFFD700),
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color(0xFFFFD700),
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = Color(0xFFFFD700),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                // Extras
                Text(
                    text = "Extras",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2A2A2A)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Insurance
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Security,
                                    contentDescription = null,
                                    tint = Color(0xFFFFD700)
                                )
                                Column {
                                    Text(
                                        text = "Insurance",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "$50/day",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                            Switch(
                                checked = addInsurance,
                                onCheckedChange = { addInsurance = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color(0xFFFFD700),
                                    checkedTrackColor = Color(0xFFFFD700).copy(alpha = 0.3f)
                                )
                            )
                        }

                        // GPS
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.GpsFixed,
                                    contentDescription = null,
                                    tint = Color(0xFFFFD700)
                                )
                                Column {
                                    Text(
                                        text = "GPS Navigation",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "$15/day",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                            Switch(
                                checked = addGPS,
                                onCheckedChange = { addGPS = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color(0xFFFFD700),
                                    checkedTrackColor = Color(0xFFFFD700).copy(alpha = 0.3f)
                                )
                            )
                        }

                        // Child Seat
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.ChildCare,
                                    contentDescription = null,
                                    tint = Color(0xFFFFD700)
                                )
                                Column {
                                    Text(
                                        text = "Child Seat",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "$20/day",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                            Switch(
                                checked = addChildSeat,
                                onCheckedChange = { addChildSeat = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color(0xFFFFD700),
                                    checkedTrackColor = Color(0xFFFFD700).copy(alpha = 0.3f)
                                )
                            )
                        }
                    }
                }

                // Price Summary
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2A2A2A)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Price Summary",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Divider(color = Color.Gray.copy(alpha = 0.3f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Car rental ($totalDays days)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                            Text(
                                text = "$$basePrice",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        }

                        if (addInsurance) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Insurance",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "$$insurancePrice",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White
                                )
                            }
                        }

                        if (addGPS) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "GPS Navigation",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "$$gpsPrice",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White
                                )
                            }
                        }

                        if (addChildSeat) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Child Seat",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "$$childSeatPrice",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White
                                )
                            }
                        }

                        Divider(color = Color.Gray.copy(alpha = 0.3f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "$$totalPrice",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFD700)
                            )
                        }
                    }
                }

                // Book Button
                Button(
                    onClick = { 
                        if (pickupLocation.isNotBlank() && dropoffLocation.isNotBlank()) {
                            showConfirmDialog = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFD700)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Proceed to Payment",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }

    // Confirmation Dialog
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmDialog = false
                        navController.navigate("payment/${car.id}/$totalPrice")
                    }
                ) {
                    Text("Confirm", color = Color(0xFFFFD700))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmDialog = false }
                ) {
                    Text("Cancel", color = Color.Gray)
                }
            },
            title = {
                Text("Confirm Booking", color = Color.White)
            },
            text = {
                Text(
                    "Are you sure you want to book ${car.name} for $totalDays days at $$totalPrice?",
                    color = Color.Gray
                )
            },
            containerColor = Color(0xFF2A2A2A),
            titleContentColor = Color.White,
            textContentColor = Color.Gray
        )
    }
}