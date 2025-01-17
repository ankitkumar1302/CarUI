package com.ankit.rental_cars_ui.presentation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.LibraryBooks
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit = {},
    hazeState: HazeState
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    NavigationBar(
        modifier = modifier
            .clip(RoundedCornerShape(26.dp))
            .height(60.dp)
            .hazeChild(
                state = hazeState,
                style = HazeStyle(
                    tint = Color.Black.copy(alpha = 0.4f),
                    blurRadius = 20.dp
                )
            ),
        containerColor = Color.Transparent,
        tonalElevation = 0.dp,
        windowInsets = WindowInsets(0.dp)
    ) {
        bottomBarItems.forEachIndexed { index, item ->
            val selected = selectedIndex == index
            val scale by animateFloatAsState(
                targetValue = if (selected) 1.1f else 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )

            NavigationBarItem(
                selected = selected,
                onClick = {
                    selectedIndex = index
                    onNavigate(index)
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                            }
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.matchParentSize(),
                            tint = if (selected) Color.White else Color.White.copy(alpha = 0.6f)
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White.copy(alpha = 0.6f),
                    indicatorColor = Color.White.copy(alpha = 0.08f)
                ),
                interactionSource = remember { MutableInteractionSource() }
            )
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