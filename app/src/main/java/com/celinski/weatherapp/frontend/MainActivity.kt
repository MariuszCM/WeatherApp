package com.celinski.weatherapp.frontend

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.celinski.weatherapp.frontend.ui.theme.DarkGreen
import com.celinski.weatherapp.frontend.ui.theme.DeepGreen
import com.celinski.weatherapp.frontend.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))
        setContent {
            WeatherAppTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (viewModel.state.isLoading) {
                        Text(
                            text = "Loading...",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    } else if (viewModel.state.city == null) {
                        // Wyświetl komunikat o oczekiwaniu na dane przez określony czas
                        Text(
                            text = "Loading...",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(DarkGreen),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Hello in ${viewModel.state.city}!",
                                fontSize = 40.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            WeatherCard(state = viewModel.state, backgroundColor = DeepGreen)
                            Spacer(modifier = Modifier.height(16.dp))
                            WeatherForecast(state = viewModel.state)
                        }
                        if (viewModel.state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        viewModel.state.error?.let { error ->
                            Text(
                                text = error,
                                color = Color.Red,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}