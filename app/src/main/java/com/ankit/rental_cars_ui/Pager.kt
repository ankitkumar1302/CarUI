package com.ankit.rental_cars_ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ankit.rental_cars_ui.ui.theme.Primary

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Pager(
    modifier: Modifier = Modifier,
    onCategorySelected: (Int) -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf(0) }
    val categories = listOf("Luxurious", "VIP Cars")

    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedContent(
            targetState = selectedCategory,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInHorizontally { it } + fadeIn()) togetherWith
                            (slideOutHorizontally { -it } + fadeOut())
                } else {
                    (slideInHorizontally { -it } + fadeIn()) togetherWith
                            (slideOutHorizontally { it } + fadeOut())
                }.using(SizeTransform(clip = false))
            }
        ) { targetCategory ->
            Text(
                text = if (targetCategory == 0) "Luxurious\nRental Cars" else "VIP\nRental Cars",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                lineHeight = 40.sp,
                modifier = Modifier.padding(horizontal = 22.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        TabRow(
            selectedTabIndex = selectedCategory,
            modifier = Modifier.padding(horizontal = 22.dp),
            containerColor = Color.Transparent,
            contentColor = Color.White,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedCategory])
                        .clip(RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp)),
                    height = 2.dp,
                    color = Color.White
                )
            },
            divider = {}
        ) {
            categories.forEachIndexed { index, category ->
                val selected = selectedCategory == index
                Tab(
                    selected = selected,
                    onClick = {
                        selectedCategory = index
                        onCategorySelected(index)
                    },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = category,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (selected) Color.White else Color.White.copy(alpha = 0.6f)
                            )
                            if (index == 1) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .alpha(0.7f)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Primary)
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "New",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(alpha = 0.6f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}























