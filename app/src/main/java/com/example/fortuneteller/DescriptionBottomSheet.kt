package com.example.fortuneteller

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fortuneteller.ui.theme.*

@Composable
fun DescriptionBottomSheet(
    id: Int
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val cardWidth = (screenWidth / 2).dp
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

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.90f)
            .background(overlay_light),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
            backgroundColor = overlay_light,
            elevation = 5.dp
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .height(36.dp)
                        .fillMaxWidth()
                ) {
                    Canvas(
                        modifier = Modifier
                            .height(4.dp)
                            .width(38.dp)
                            .align(Alignment.Center)
                    ) {
                        drawLine(
                            color = gray,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 4.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }


                Text(
                    text = getTitle(id = id),
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    fontFamily = RobotoBold,
                    color = white,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .fillMaxWidth()
                )
            }
        }
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    alpha = 1f - ((scrollState.value.toFloat() / scrollState.maxValue) * 1.5f)
                    translationY = 0.5f * scrollState.value
                }
            ) {
                Card(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .width(cardWidth)
                        .height(cardHeight)
                        .align(Alignment.Center),
                    elevation = 10.dp,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(
                        1.dp,
                        Brush.horizontalGradient(colors = listOf(color, color2))
                    )
                ) {
                    Image(
                        painter = getImage(id = id),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Text(
                text = getDescription(id = id),
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                fontFamily = RobotoRegular,
                color = white,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(overlay_light)
            )
        }
    }
}