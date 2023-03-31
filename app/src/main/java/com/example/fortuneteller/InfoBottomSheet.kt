package com.example.fortuneteller

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fortuneteller.ui.theme.RobotoBold
import com.example.fortuneteller.ui.theme.RobotoRegular
import com.example.fortuneteller.ui.theme.overlay_light
import com.example.fortuneteller.ui.theme.white

@Composable
fun InfoBottomSheet() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .background(overlay_light)
    ) {
        Text(
            text = stringResource(R.string.how_read_cards),
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis,
            fontFamily = RobotoBold,
            color = white,
            maxLines = 2,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.instruction),
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis,
            fontFamily = RobotoRegular,
            color = white,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}