package com.ankit.rental_cars_ui.presentation.screens.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ankit.rental_cars_ui.presentation.components.TopBar
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(hazeState: HazeState) {
    var darkMode by remember { mutableStateOf(true) }
    var notifications by remember { mutableStateOf(true) }
    var biometrics by remember { mutableStateOf(false) }
    var emailNotifications by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
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

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF1E1E1E),
                                    Color.Transparent
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .hazeChild(
                                state = hazeState,
                                style = HazeStyle(
                                    tint = Color(0xFF1E1E1E).copy(alpha = 0.5f),
                                    blurRadius = 16.dp
                                )
                            ),
                        color = Color.Transparent
                    ) {
                        TopBar(
                            title = "Settings"
                        )
                    }
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Profile Settings Section
                item {
                    Text(
                        text = "Profile Settings",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF121212)
                        )
                    ) {
                        Column {
                            SettingsItem(
                                title = "Edit Profile",
                                subtitle = "Update your personal information",
                                icon = Icons.Default.Person,
                                iconTint = Color(0xFF64B5F6),
                                onClick = { }
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = Color(0xFF2A2A2A)
                            )
                            SettingsItem(
                                title = "Change Password",
                                subtitle = "Update your security credentials",
                                icon = Icons.Default.Lock,
                                iconTint = Color(0xFF81C784),
                                onClick = { }
                            )
                        }
                    }
                }

                // Preferences Section
                item {
                    Text(
                        text = "Preferences",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF121212)
                        )
                    ) {
                        Column {
                            SettingsItem(
                                title = "Dark Mode",
                                subtitle = "Toggle dark theme",
                                icon = Icons.Default.DarkMode,
                                iconTint = Color(0xFF9575CD),
                                hasSwitch = true,
                                switchValue = darkMode,
                                onSwitchChange = { darkMode = it }
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = Color(0xFF2A2A2A)
                            )
                            SettingsItem(
                                title = "Notifications",
                                subtitle = "Manage push notifications",
                                icon = Icons.Default.Notifications,
                                iconTint = Color(0xFFFFB74D),
                                hasSwitch = true,
                                switchValue = notifications,
                                onSwitchChange = { notifications = it }
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = Color(0xFF2A2A2A)
                            )
                            SettingsItem(
                                title = "Biometric Login",
                                subtitle = "Enable fingerprint authentication",
                                icon = Icons.Default.Fingerprint,
                                iconTint = Color(0xFFE57373),
                                hasSwitch = true,
                                switchValue = biometrics,
                                onSwitchChange = { biometrics = it }
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = Color(0xFF2A2A2A)
                            )
                            SettingsItem(
                                title = "Email Notifications",
                                subtitle = "Receive updates via email",
                                icon = Icons.Default.Email,
                                iconTint = Color(0xFF4DB6AC),
                                hasSwitch = true,
                                switchValue = emailNotifications,
                                onSwitchChange = { emailNotifications = it }
                            )
                        }
                    }
                }

                // Bottom spacing
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
private fun SettingsItem(
    title: String,
    subtitle: String? = null,
    icon: ImageVector,
    iconTint: Color,
    hasSwitch: Boolean = false,
    switchValue: Boolean = false,
    onSwitchChange: ((Boolean) -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconTint.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
        }

        // Text
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }

        // Switch or Arrow
        if (hasSwitch) {
            Switch(
                checked = switchValue,
                onCheckedChange = { onSwitchChange?.invoke(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = iconTint.copy(alpha = 0.5f),
                    uncheckedThumbColor = Color.White.copy(alpha = 0.7f),
                    uncheckedTrackColor = Color.White.copy(alpha = 0.2f)
                )
            )
        } else if (onClick != null) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.6f)
            )
        }
    }
} 