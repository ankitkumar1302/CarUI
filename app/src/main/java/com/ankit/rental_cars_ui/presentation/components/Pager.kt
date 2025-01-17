package com.ankit.rental_cars_ui.presentation.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
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
    selectedCategory: Int,
    onCategorySelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    categories: List<String> = listOf("Luxurious", "VIP Cars"),
    titles: List<String> = listOf("Luxurious\nRental Cars", "VIP\nRental Cars")
) {
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
                text = titles[targetCategory],
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 40.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            categories.forEachIndexed { index, category ->
                val isSelected = selectedCategory == index
                Card(
                    onClick = { onCategorySelected(index) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) Color(0xFF2A2A2A) else Color(0xFF1E1E1E)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = category,
                            fontSize = 16.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f)
                        )
                        if (index == 1) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFFFA726).copy(alpha = 0.2f)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(4.dp)
                            ) {
                                Text(
                                    text = "NEW",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFA726),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
} 