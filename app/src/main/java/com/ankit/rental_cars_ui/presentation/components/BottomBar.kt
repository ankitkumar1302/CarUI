package com.ankit.rental_cars_ui.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.LibraryBooks
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
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
    currentRoute: String = "home"
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
                        Color.Black.copy(alpha = 0.08f)
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
                        tint = Color.Black.copy(alpha = 0.08f),
                        blurRadius = 16.dp
                    )
                ),
            containerColor = Color.White.copy(alpha = 0.03f),
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
                                    Color.White.copy(alpha = 0.35f)
                            )
                        }
                    },
                    label = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (selected) Color.White else Color.White.copy(alpha = 0.35f)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.White.copy(alpha = 0.35f),
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.White.copy(alpha = 0.35f),
                        indicatorColor = Color.Transparent
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
        icon = Icons.Rounded.Home
    ),
    BottomBarItem(
        title = "Account",
        icon = Icons.Rounded.AccountBox
    ),
    BottomBarItem(
        title = "Analytics",
        icon = Icons.AutoMirrored.Rounded.LibraryBooks
    ),
    BottomBarItem(
        title = "Settings",
        icon = Icons.Rounded.Settings
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