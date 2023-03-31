package com.example.fortuneteller

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.fortuneteller.ui.theme.FortuneTellerTheme
import com.gadalka.MainScreen
import com.gadalka.mvi.State
import com.gadalka.mvi.ViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FortuneTellerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // A surface container using the 'background' color from the theme
                    val viewModel = remember { ViewModel(State()) }
                    val state by viewModel.state.collectAsState()
                    MainScreen(state = state, performIntent = viewModel::performIntent)
                }
            }
        }
    }
}