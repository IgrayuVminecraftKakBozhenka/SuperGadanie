package com.gadalka

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fortuneteller.AutoSizeTextField
import com.example.fortuneteller.DescriptionBottomSheet
import com.example.fortuneteller.DisabledCardItem
import com.example.fortuneteller.InfoBottomSheet
import com.example.fortuneteller.R
import com.example.fortuneteller.ui.theme.*
import com.gadalka.mvi.Intent
import com.gadalka.mvi.State
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(state: State, performIntent: (Intent) -> Unit) {

    val cardWidth = 200.dp
    val cardOffset = animateDpAsState(cardWidth * state.offset)
    val bottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden) { value ->
            if (value == ModalBottomSheetValue.Hidden) {
                performIntent(Intent.ClearDescriptions)
            }
            true
        }

    val lazyState = rememberLazyListState()

    val context = LocalView.current.context

    val vibrator = remember {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(VIBRATOR_SERVICE) as Vibrator
            }
        }

    val sensorManager = remember {(context.getSystemService(Context.SENSOR_SERVICE) as SensorManager)}
    val accelerometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    val listener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(p0: SensorEvent?) {
                performIntent(Intent.AccelerometerData(p0?.values ?: FloatArray(0)))
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
               // TODO("Not yet implemented")
            }

        }
    }
    sensorManager.registerListener(
        listener,  accelerometer, 1
    )

    LaunchedEffect(key1 = state.isVibrate) {
        if (state.isVibrate) {
            vibrator.vibrate(VibrationEffect.createOneShot(300L, 1))
            performIntent(Intent.TurnOffVibrate)
        }
    }

    LaunchedEffect(key1 = state.bottomSheetShown, block = {
        if (state.bottomSheetShown) {
            bottomSheetState.show()
        } else {
            bottomSheetState.hide()
        }
    })

    BackHandler(true) {
        if (state.bottomSheetShown) {
            performIntent(Intent.ClearDescriptions)
        }
    }

    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {

        ModalBottomSheetLayout(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsWithImePadding()
                .background(base),
            sheetContent = {
                if (state.isShowInfo) {
                    InfoBottomSheet()
                } else {
                    DescriptionBottomSheet(state.actualCardId)
                }

            },
            sheetState = bottomSheetState,
            sheetShape = RoundedCornerShape(16.dp)
        ) {

            if (state.isShowAd) {
                //TODO будет реклама
                Log.d("AD", "8 800 555 35 35 проще позвонить чем у когото занимать")
            }

            Column {
                TopAppBar(backgroundColor = overlay_light) {
                    Crossfade(
                        targetState = state.firstCard.id != -1 && state.secondCard.id != -1 && state.thirdCard.id != -1,
                        modifier = Modifier
                            .weight(0.8f)
                            .wrapContentWidth(Alignment.Start)
                    ) {
                        if (it) {
                            AutoSizeTextField(
                                text = stringResource(R.string.get_answer),
                                color = white,
                                textStyle = TextStyle(fontFamily = RobotoMedium, fontSize = 20.sp),
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                            )

                        } else {
                            AutoSizeTextField(
                                text = stringResource(R.string.ask_question),
                                color = white,
                                textStyle = TextStyle(fontFamily = RobotoMedium, fontSize = 20.sp),
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                            )
                        }
                    }

                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_info),
                        contentDescription = "info",
                        modifier = Modifier
                            .weight(0.2f)
                            .wrapContentWidth(Alignment.End)
                            .padding(start = 16.dp, end = 16.dp)
                            .clickable { performIntent(Intent.ShowInfo(true)) }
                    )
                }
                Card(
                    shape = RoundedCornerShape(0.dp),
                    elevation = 10.dp,
                    backgroundColor = surface
                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 32.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        CardItem(
                            id = state.firstCard.id,
                            onClick = { performIntent(Intent.CardDescriptionClick(it)) }
                        )

                        CardItem(
                            id = state.secondCard.id,
                            onClick = { performIntent(Intent.CardDescriptionClick(it)) }
                        )

                        CardItem(
                            id = state.thirdCard.id,
                            onClick = { performIntent(Intent.CardDescriptionClick(it)) }
                        )
                    }
                }

                LazyRow(
                    modifier = Modifier.padding(top = 16.dp),
                    state = lazyState
                ) {
                    itemsIndexed(state.allCards) { index, item ->
                        DisabledCardItem(
                            id = item.id,
                            cardOffset =
                            if (index % 2 == 0) 0.dp else -(cardOffset.value),
                            onClick = { performIntent(Intent.OnCardClick(it)) }
                        )
                    }
                }

                Button(
                    onClick = {
                        performIntent(Intent.ShuffleCards)
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .height(56.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.shuffle),
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = white,
                        fontFamily = RobotoMedium,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                }
            }
        }
    }
}

