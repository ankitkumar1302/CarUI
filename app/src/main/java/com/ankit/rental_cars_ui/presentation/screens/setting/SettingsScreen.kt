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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
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
            .background(Color(0xFF1E1E1E))
    ) {
        // Background gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2C2C2C),
                            Color(0xFF1E1E1E)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            // Top Section with Title
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    Text(
                        text = "Settings",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Customize your app preferences",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Appearance Section
                item {
                    SettingsSection(
                        title = "Appearance",
                        items = listOf(
                            SettingsItem(
                                title = "Dark Mode",
                                subtitle = "Enable dark theme",
                                icon = Icons.Default.DarkMode,
                                hasSwitch = true,
                                switchValue = darkMode,
                                onSwitchChange = { darkMode = it }
                            )
                        ),
                        hazeState = hazeState
                    )
                }

                // Notifications Section
                item {
                    SettingsSection(
                        title = "Notifications",
                        items = listOf(
                            SettingsItem(
                                title = "Push Notifications",
                                subtitle = "Get notified about important updates",
                                icon = Icons.Default.Notifications,
                                hasSwitch = true,
                                switchValue = notifications,
                                onSwitchChange = { notifications = it }
                            ),
                            SettingsItem(
                                title = "Email Notifications",
                                subtitle = "Receive updates via email",
                                icon = Icons.Default.Email,
                                hasSwitch = true,
                                switchValue = emailNotifications,
                                onSwitchChange = { emailNotifications = it }
                            )
                        ),
                        hazeState = hazeState
                    )
                }

                // Security Section
                item {
                    SettingsSection(
                        title = "Security",
                        items = listOf(
                            SettingsItem(
                                title = "Biometric Authentication",
                                subtitle = "Use fingerprint or face ID",
                                icon = Icons.Default.Fingerprint,
                                hasSwitch = true,
                                switchValue = biometrics,
                                onSwitchChange = { biometrics = it }
                            ),
                            SettingsItem(
                                title = "Change Password",
                                subtitle = "Update your account password",
                                icon = Icons.Default.Lock,
                                hasSwitch = false,
                                onClick = { /* Handle password change */ }
                            )
                        ),
                        hazeState = hazeState
                    )
                }

                // About Section
                item {
                    SettingsSection(
                        title = "About",
                        items = listOf(
                            SettingsItem(
                                title = "Version",
                                subtitle = "1.0.0",
                                icon = Icons.Default.Info,
                                hasSwitch = false
                            ),
                            SettingsItem(
                                title = "Terms of Service",
                                subtitle = "Read our terms and conditions",
                                icon = Icons.Default.Description,
                                hasSwitch = false,
                                onClick = { /* Handle terms */ }
                            ),
                            SettingsItem(
                                title = "Privacy Policy",
                                subtitle = "Read our privacy policy",
                                icon = Icons.Default.Security,
                                hasSwitch = false,
                                onClick = { /* Handle privacy */ }
                            )
                        ),
                        hazeState = hazeState
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    items: List<SettingsItem>,
    hazeState: HazeState
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .hazeChild(hazeState),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.1f)
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                items.forEachIndexed { index, item ->
                    SettingsItemRow(item = item)
                    if (index < items.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.White.copy(alpha = 0.1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsItemRow(item: SettingsItem) {
    ListItem(
        headlineContent = {
            Text(
                text = item.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        },
        supportingContent = item.subtitle?.let {
            {
                Text(
                    text = it,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        },
        trailingContent = {
            if (item.hasSwitch) {
                Switch(
                    checked = item.switchValue ?: false,
                    onCheckedChange = item.onSwitchChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color.White.copy(alpha = 0.5f),
                        uncheckedThumbColor = Color.White.copy(alpha = 0.7f),
                        uncheckedTrackColor = Color.White.copy(alpha = 0.2f)
                    )
                )
            }
        },
        modifier = Modifier.clickable(
            enabled = item.onClick != null,
            onClick = { item.onClick?.invoke() }
        )
    )
}

private data class SettingsItem(
    val title: String,
    val subtitle: String? = null,
    val icon: ImageVector,
    val hasSwitch: Boolean = false,
    val switchValue: Boolean? = null,
    val onSwitchChange: ((Boolean) -> Unit)? = null,
    val onClick: (() -> Unit)? = null
) 