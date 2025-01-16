package com.ankit.rental_cars_ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Settings",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.hazeChild(hazeState)
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Appearance",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                ListItem(
                    headlineContent = {
                        Text(
                            "Dark Mode",
                            color = Color.White
                        )
                    },
                    leadingContent = {
                        Icon(
                            Icons.Default.DarkMode,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = darkMode,
                            onCheckedChange = { darkMode = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color.White.copy(alpha = 0.5f)
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .hazeChild(hazeState),
                    colors = ListItemDefaults.colors(
                        containerColor = Color.White.copy(alpha = 0.1f)
                    )
                )
            }

            item {
                Text(
                    text = "Notifications",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                ListItem(
                    headlineContent = {
                        Text(
                            "Push Notifications",
                            color = Color.White
                        )
                    },
                    leadingContent = {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = notifications,
                            onCheckedChange = { notifications = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color.White.copy(alpha = 0.5f)
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .hazeChild(hazeState),
                    colors = ListItemDefaults.colors(
                        containerColor = Color.White.copy(alpha = 0.1f)
                    )
                )
            }

            item {
                Text(
                    text = "About",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                ListItem(
                    headlineContent = {
                        Text(
                            "Version",
                            color = Color.White
                        )
                    },
                    supportingContent = {
                        Text(
                            "1.0.0",
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    },
                    leadingContent = {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .hazeChild(hazeState),
                    colors = ListItemDefaults.colors(
                        containerColor = Color.White.copy(alpha = 0.1f)
                    )
                )
            }
        }
    }
} 