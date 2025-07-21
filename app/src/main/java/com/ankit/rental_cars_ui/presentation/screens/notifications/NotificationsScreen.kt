package com.ankit.rental_cars_ui.presentation.screens.notifications

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ankit.rental_cars_ui.presentation.components.TopBar
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState

data class Notification(
    val id: Int,
    val title: String,
    val message: String,
    val timestamp: LocalDateTime,
    val type: NotificationType,
    val isRead: Boolean = false
)

enum class NotificationType {
    BOOKING_CONFIRMED,
    BOOKING_REMINDER,
    PROMOTION,
    SYSTEM,
    REVIEW_REQUEST
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    navController: NavController,
    hazeState: HazeState
) {
    // Sample notifications - in real app this would come from ViewModel
    var notifications by remember {
        mutableStateOf(
            listOf(
                Notification(
                    id = 1,
                    title = "Booking Confirmed!",
                    message = "Your booking for Ferrari SF90 Stradale has been confirmed for tomorrow.",
                    timestamp = LocalDateTime.now().minusHours(2),
                    type = NotificationType.BOOKING_CONFIRMED,
                    isRead = false
                ),
                Notification(
                    id = 2,
                    title = "Don't forget your booking",
                    message = "Your Porsche 911 Turbo S rental starts in 2 hours. Don't forget to bring your documents!",
                    timestamp = LocalDateTime.now().minusHours(5),
                    type = NotificationType.BOOKING_REMINDER,
                    isRead = false
                ),
                Notification(
                    id = 3,
                    title = "Special Weekend Offer!",
                    message = "Get 20% off on all luxury cars this weekend. Book now!",
                    timestamp = LocalDateTime.now().minusDays(1),
                    type = NotificationType.PROMOTION,
                    isRead = true
                ),
                Notification(
                    id = 4,
                    title = "Rate your experience",
                    message = "How was your experience with Rolls-Royce Phantom? Leave a review!",
                    timestamp = LocalDateTime.now().minusDays(2),
                    type = NotificationType.REVIEW_REQUEST,
                    isRead = true
                )
            )
        )
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
                title = "Notifications",
                onBackClick = { navController.popBackStack() },
                actions = {
                    if (notifications.any { !it.isRead }) {
                        TextButton(
                            onClick = {
                                notifications = notifications.map { it.copy(isRead = true) }
                            }
                        ) {
                            Text(
                                text = "Mark all read",
                                color = Color(0xFFFFD700)
                            )
                        }
                    }
                }
            )

            // Notification Stats
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2A2A2A)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${notifications.size}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD700)
                        )
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                    
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(40.dp)
                            .background(Color.Gray.copy(alpha = 0.3f))
                    )
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${notifications.count { !it.isRead }}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Unread",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Notifications List
            if (notifications.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.NotificationsNone,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = "No notifications yet",
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
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(notifications) { notification ->
                        NotificationItem(
                            notification = notification,
                            onMarkAsRead = {
                                notifications = notifications.map {
                                    if (it.id == notification.id) it.copy(isRead = true) else it
                                }
                            },
                            onDelete = {
                                notifications = notifications.filter { it.id != notification.id }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationItem(
    notification: Notification,
    onMarkAsRead: () -> Unit,
    onDelete: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.Red.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        },
        content = {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (!notification.isRead) {
                                onMarkAsRead()
                            }
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (!notification.isRead) Color(0xFF2A2A2A) else Color(0xFF1E1E1E)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Icon
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(
                                    when (notification.type) {
                                        NotificationType.BOOKING_CONFIRMED -> Color(0xFF4CAF50).copy(alpha = 0.2f)
                                        NotificationType.BOOKING_REMINDER -> Color(0xFF2196F3).copy(alpha = 0.2f)
                                        NotificationType.PROMOTION -> Color(0xFFFFD700).copy(alpha = 0.2f)
                                        NotificationType.SYSTEM -> Color(0xFF9E9E9E).copy(alpha = 0.2f)
                                        NotificationType.REVIEW_REQUEST -> Color(0xFF9C27B0).copy(alpha = 0.2f)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = when (notification.type) {
                                    NotificationType.BOOKING_CONFIRMED -> Icons.Rounded.CheckCircle
                                    NotificationType.BOOKING_REMINDER -> Icons.Rounded.Schedule
                                    NotificationType.PROMOTION -> Icons.Rounded.LocalOffer
                                    NotificationType.SYSTEM -> Icons.Rounded.Info
                                    NotificationType.REVIEW_REQUEST -> Icons.Rounded.Star
                                },
                                contentDescription = null,
                                tint = when (notification.type) {
                                    NotificationType.BOOKING_CONFIRMED -> Color(0xFF4CAF50)
                                    NotificationType.BOOKING_REMINDER -> Color(0xFF2196F3)
                                    NotificationType.PROMOTION -> Color(0xFFFFD700)
                                    NotificationType.SYSTEM -> Color(0xFF9E9E9E)
                                    NotificationType.REVIEW_REQUEST -> Color(0xFF9C27B0)
                                },
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Content
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = notification.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.weight(1f)
                                )
                                
                                if (!notification.isRead) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFFFD700))
                                    )
                                }
                            }
                            
                            Text(
                                text = notification.message,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                            
                            Text(
                                text = formatTimestamp(notification.timestamp),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun formatTimestamp(timestamp: LocalDateTime): String {
    val now = LocalDateTime.now()
    val hours = java.time.Duration.between(timestamp, now).toHours()
    
    return when {
        hours < 1 -> "Just now"
        hours < 24 -> "$hours hours ago"
        hours < 48 -> "Yesterday"
        else -> timestamp.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    }
}