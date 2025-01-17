package com.ankit.rental_cars_ui.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit = {},
    hazeState: HazeState,
    currentRoute: String = "home",
    isHomeScreen: Boolean = true
) {
    val selectedIndex = when (currentRoute) {
        "home" -> 0
        "account" -> 1
        "analytics" -> 2
        "settings" -> 3
        else -> 0
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color(0xFF1A1A1A).copy(alpha = 0.95f),
                        Color(0xFF1A1A1A)
                    ),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .hazeChild(
                    state = hazeState,
                    style = HazeStyle(
                        tint = Color(0xFF1A1A1A).copy(alpha = 0.8f),
                        blurRadius = 16.dp
                    )
                ),
            containerColor = Color.Transparent,
            contentColor = Color.White,
            tonalElevation = 0.dp,
            windowInsets = WindowInsets(0)
        ) {
            bottomBarItems.forEachIndexed { index, item ->
                val selected = selectedIndex == index
                val scale by animateFloatAsState(
                    targetValue = if (selected) 1.1f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    ),
                    label = "scale"
                )

                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigate(index) },
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                modifier = Modifier.size(if (selected) 22.dp else 20.dp),
                                tint = if (selected) 
                                    Color.White 
                                else 
                                    Color.White.copy(alpha = 0.5f)
                            )
                        }
                    },
                    label = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (selected) Color.White else Color.White.copy(alpha = 0.5f)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.White.copy(alpha = 0.5f),
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.White.copy(alpha = 0.5f),
                        indicatorColor = Color(0xFF2A2A2A)
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )
            }
        }
    }
}

data class BottomBarItem(
    val title: String,
    val icon: ImageVector
)

val bottomBarItems = listOf(
    BottomBarItem(
        title = "Home",
        icon = Icons.Default.Home
    ),
    BottomBarItem(
        title = "Account",
        icon = Icons.Default.Person
    ),
    BottomBarItem(
        title = "Analytics",
        icon = Icons.Default.Analytics
    ),
    BottomBarItem(
        title = "Settings",
        icon = Icons.Default.Settings
    )
)

@Composable
@Preview
fun BottomBarPreview() {
    BottomBar(
        onNavigate = {},
        hazeState = rememberSaveable { HazeState() }
    )
}