package com.shihab.weather_app_compose.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shihab.weather_app_compose.ui.components.CurrentWeatherCard
import com.shihab.weather_app_compose.viewmodel.WeatherUiState
import com.shihab.weather_app_compose.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {

    val uiState = viewModel.weatherUiState.value
    Log.d("WeatherScreen", "WeatherUiState: $uiState")


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Real-Time Weather", fontWeight = FontWeight.Bold) },
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            when (uiState) {
                is WeatherUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is WeatherUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Error: ${uiState.message}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                is WeatherUiState.Success -> {
                    CurrentWeatherCard(weather = uiState.weather.iconCode.let {
                        com.shihab.weather_app_compose.data.CurrentWeather(
                            city = uiState.weather.city,
                            temperature = uiState.weather.temperature,
                            description = uiState.weather.description,
                            humidity = uiState.weather.humidity,
                            windSpeed = uiState.weather.windSpeed,
                            iconVector = com.shihab.weather_app_compose.ui.components.getWeatherIcon(
                                when (it) {
                                    "01d", "01n" -> Icons.Default.WbSunny
                                    "02d", "02n", "03d", "03n", "04d", "04n" -> Icons.Default.Cloud
                                    "09d", "09n", "10d", "10n" -> Icons.Default.WaterDrop
                                    "11d", "11n" -> Icons.Default.FlashOn
                                    "13d", "13n" -> Icons.Default.AcUnit
                                    else -> Icons.Default.WbSunny
                                }
                            )
                        )
                    })

                    Text(
                        text = "5-Day Forecast (API Integration Needed)",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}