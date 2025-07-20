import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Power
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ankit.rental_cars_ui.domain.model.Car
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailScreen(
    car: Car,
    onBackClick: () -> Unit,
    hazeState: HazeState,
    onBookClick: () -> Unit = {}
) {
    var isFavorite by remember { mutableStateOf(false) }
    var showRentDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    
    // Calculate scroll progress
    val scrollProgress by remember {
        derivedStateOf {
            if (scrollState.maxValue == 0) 0f
            else scrollState.value.toFloat() / scrollState.maxValue
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ) {
        // Background Car Image (Blurred)
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
        ) {
            Image(
                painter = painterResource(car.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .graphicsLayer { 
                        alpha = 0.15f - (scrollProgress * 0.1f)
                    }
                    .haze(
                        state = hazeState,
                        style = HazeStyle(
                            blurRadius = 30.dp,
                            tint = Color.Black.copy(alpha = 0.1f)
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            // Top Actions Row with Progress
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Progress Bar
                LinearProgressIndicator(
                    progress = scrollProgress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .clip(RoundedCornerShape(1.dp))
                        .align(Alignment.TopCenter),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.2f)
                )
                
                // Actions Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(
                            onClick = { isFavorite = !isFavorite },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) Color.Red else Color.White
                            )
                        }
                        IconButton(
                            onClick = { /* Handle share */ },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Share,
                                contentDescription = "Share",
                                tint = Color.White
                            )
                        }
                    }
                }
            }

            // Car Image Container with Scroll Effect
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .offset(y = (-20).dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(car.image),
                    contentDescription = car.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .graphicsLayer {
                            // Scale and fade effect based on scroll
                            val scale = 1f - (scrollProgress * 0.2f)
                            scaleX = scale
                            scaleY = scale
                            alpha = 1f - (scrollProgress * 0.5f)
                        }
                )
            }

            // Content Section with Scroll Animation
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        // Slide up effect based on scroll
                        translationY = -scrollProgress * 50f
                    },
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color(0xFF1E1E1E)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(horizontal = 24.dp)
                            .padding(top = 32.dp, bottom = 100.dp)
                    ) {
                        // Car Info Header with Fade In
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .graphicsLayer {
                                    alpha = 1f - (scrollProgress * 0.5f)
                                },
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = car.name,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(car.logo),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(Color.White)
                                            .padding(4.dp)
                                    )
                                    Text(
                                        text = "Premium",
                                        fontSize = 16.sp,
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                }
                                Text(
                                    text = "$${car.price}/day",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Features Section
                        Text(
                            text = "Specifications",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            FeatureItem(
                                icon = Icons.Rounded.Speed,
                                title = "Speed",
                                value = "280 km/h"
                            )
                            FeatureItem(
                                icon = Icons.Rounded.Timer,
                                title = "0-100 km/h",
                                value = "3.2s"
                            )
                            FeatureItem(
                                icon = Icons.Rounded.Power,
                                title = "Power",
                                value = "580 HP"
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Description
                        Text(
                            text = "About",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Experience luxury and performance in perfect harmony. This ${car.name} combines cutting-edge technology with elegant design, offering an unparalleled driving experience.\n\nFeaturing state-of-the-art aerodynamics, premium leather interiors, and advanced driver assistance systems, this vehicle sets new standards in automotive excellence. The powerful engine delivers exhilarating performance while maintaining exceptional efficiency.\n\nEvery detail has been meticulously crafted to ensure maximum comfort and driving pleasure. The advanced suspension system provides a smooth ride while maintaining precise handling characteristics.",
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.7f),
                            lineHeight = 24.sp
                        )
                    }

                    // Bottom Button with Gradient
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF1E1E1E).copy(alpha = 0.0f),
                                        Color(0xFF1E1E1E).copy(alpha = 0.95f),
                                        Color(0xFF1E1E1E)
                                    )
                                )
                            )
                            .padding(horizontal = 24.dp, vertical = 16.dp)
                    ) {
                        Button(
                            onClick = onBookClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(28.dp)
                        ) {
                            Text(
                                text = "Book Now",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureItem(
    icon: ImageVector,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.1f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = title,
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}