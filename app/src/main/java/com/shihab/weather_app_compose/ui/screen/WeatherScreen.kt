package com.shihab.weather_app_compose.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.shihab.weather_app_compose.ui.getDynamicGradient
import com.shihab.weather_app_compose.ui.getWeatherAnimation
import com.shihab.weather_app_compose.viewmodel.WeatherUiState
import com.shihab.weather_app_compose.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val uiState = viewModel.weatherUiState.value
    var searchText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val currentIconCode = if (uiState is WeatherUiState.Success) uiState.weather.iconCode else "01d"
    val backgroundBrush = getDynamicGradient(currentIconCode)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search City...", color = Color.White.copy(alpha = 0.7f)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.2f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.2f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    if (searchText.isNotEmpty()) {
                        viewModel.fetchWeather(searchText)
                        focusManager.clearFocus()
                    }
                }),
                trailingIcon = {
                    IconButton(onClick = {
                        if (searchText.isNotEmpty()) {
                            viewModel.fetchWeather(searchText)
                            focusManager.clearFocus()
                        }
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            when (uiState) {
                is WeatherUiState.Idle -> {
                    Text("Enter a city name", color = Color.White, fontSize = 18.sp)
                }
                is WeatherUiState.Loading -> {
                    CircularProgressIndicator(color = Color.White)
                }
                is WeatherUiState.Error -> {
                    Text(text = uiState.message, color = Color.Red, fontWeight = FontWeight.Bold)
                }
                is WeatherUiState.Success -> {
                    // --- LOTTIE ANIMATION ---
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(getWeatherAnimation(uiState.weather.iconCode)))
                    val progress by animateLottieCompositionAsState(
                        composition = composition,
                        iterations = LottieConstants.IterateForever
                    )

                    LottieAnimation(
                        composition = composition,
                        progress = { progress },
                        modifier = Modifier.size(200.dp)
                    )
                    // ------------------------

                    Text(
                        text = uiState.weather.city,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${uiState.weather.temperature}°C",
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = uiState.weather.description,
                        fontSize = 20.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Forecast",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.forecastList) { forecast ->
                            ForecastGlassCard(
                                time = forecast.time,
                                temp = forecast.temp,
                                iconCode = forecast.iconCode
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastGlassCard(time: String, temp: Int, iconCode: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f)), // গ্লাস ইফেক্ট
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.width(100.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = time, color = Color.White, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(getWeatherAnimation(iconCode)))
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$temp°", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}