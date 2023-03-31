package com.gadalka

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.fortuneteller.getImage
import com.example.fortuneteller.ui.theme.blue
import com.example.fortuneteller.ui.theme.lightBlue

@Composable
fun CardItem(
    id: Int,
    onClick: (Int) -> Unit
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val cardWidth = (screenWidth.dp - 64.dp) / 3
    val cardHeight = (cardWidth.value * 1.5).dp

    val infiniteTransition = rememberInfiniteTransition()

    val color by infiniteTransition.animateColor(
        initialValue = lightBlue,
        targetValue = blue,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val color2 by infiniteTransition.animateColor(
        initialValue = blue,
        targetValue = lightBlue,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val rotation = remember { Animatable(initialValue = 180f) }
    LaunchedEffect(id != -1) {
        rotation.animateTo(
            targetValue = if (id != -1) 0f else 180f,
            animationSpec = spring(stiffness = Spring.StiffnessLow),
        )
    }

    Card(
        modifier = Modifier
            .height(cardHeight)
            .width(cardWidth)
            .graphicsLayer {
                rotationX = -rotation.value
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 3.dp,
        border = BorderStroke(1.dp, Brush.horizontalGradient(colors = listOf(color, color2)))
    ) {

        Crossfade(targetState = id) { id ->
            Image(
                painter = getImage(id = id),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clickable {
                        if (id != -1) onClick(id)
                    }
                    .fillMaxSize()
            )
        }
    }
}
