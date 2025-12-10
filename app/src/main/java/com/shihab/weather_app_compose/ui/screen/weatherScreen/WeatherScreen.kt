package com.shihab.weather_app_compose.ui.screen.weatherScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.shihab.weather_app_compose.ui.screen.weatherScreen.components.ForecastItemView
import com.shihab.weather_app_compose.ui.screen.weatherScreen.components.WeatherInfoItem
import com.shihab.weather_app_compose.ui.getDynamicGradient
import com.shihab.weather_app_compose.ui.getHumidityIcon
import com.shihab.weather_app_compose.ui.getPressureIcon
import com.shihab.weather_app_compose.ui.getVisibilityIcon
import com.shihab.weather_app_compose.ui.getWeatherIcon
import com.shihab.weather_app_compose.ui.getWindIcon
import com.shihab.weather_app_compose.viewmodel.WeatherUiState
import com.shihab.weather_app_compose.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(navController: NavController, viewModel: WeatherViewModel = viewModel()) {
    val uiState = viewModel.weatherUiState.value
    var searchText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    val currentIconCode = if (uiState is WeatherUiState.Success) uiState.weather.iconCode else "01d"
    val backgroundBrush = getDynamicGradient(currentIconCode)
    val contentColor = Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = {
                        Text(
                            "Search City...",
                            color = contentColor.copy(alpha = 0.7f)
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Transparent),
                    shape = RoundedCornerShape(24.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = contentColor.copy(alpha = 0.15f),
                        unfocusedContainerColor = contentColor.copy(alpha = 0.15f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = contentColor,
                        unfocusedTextColor = contentColor,
                        cursorColor = contentColor
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
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                                tint = contentColor
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(
                    onClick = { navController.navigate("settings_screen") },
                    modifier = Modifier
                        .background(contentColor.copy(alpha = 0.15f), shape = CircleShape)
                        .size(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = contentColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            when (uiState) {
                is WeatherUiState.Idle -> {
                    Text("Enter a city name", color = contentColor, fontSize = 18.sp)
                }

                is WeatherUiState.Loading -> {
                    Box(modifier = Modifier.height(400.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = contentColor)
                    }
                }

                is WeatherUiState.Error -> {
                    Text(
                        text = uiState.message,
                        color = Color.Red.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Bold
                    )
                }

                is WeatherUiState.Success -> {
                    val weather = uiState.weather

                    Text(
                        text = weather.city,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium,
                        color = contentColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = getWeatherIcon(weather.iconCode),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp),
                            tint = contentColor
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(
                                text = "${weather.temperature}°${weather.unit}",
                                fontSize = 80.sp,
                                fontWeight = FontWeight.Light,
                                color = contentColor,
                                lineHeight = 80.sp
                            )
                            Row {
                                Text(
                                    text = "${weather.tempMax}°↑",
                                    fontSize = 18.sp,
                                    color = contentColor.copy(alpha = 0.8f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${weather.tempMin}°↓",
                                    fontSize = 18.sp,
                                    color = contentColor.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = weather.description,
                        fontSize = 22.sp,
                        color = contentColor
                    )
                    Text(
                        text = "Feels like ${weather.feelsLike}°",
                        fontSize = 16.sp,
                        color = contentColor.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        WeatherInfoItem(
                            icon = getHumidityIcon(),
                            label = "Humidity",
                            value = "${weather.humidity}%"
                        )
                        WeatherInfoItem(
                            icon = getWindIcon(),
                            label = "Wind",
                            value = "${weather.windSpeed} km/h"
                        )
                        WeatherInfoItem(
                            icon = getPressureIcon(),
                            label = "Pressure",
                            value = "${weather.pressure} hPa"
                        )
                        WeatherInfoItem(
                            icon = getVisibilityIcon(),
                            label = "Visibility",
                            value = weather.visibility
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    var selectedTabIndex by remember { mutableIntStateOf(0) }
                    val tabs = listOf("Weather", "Wind", "Precip")
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = Color.Transparent,
                        contentColor = contentColor,
                        divider = {},
                        indicator = { tabPositions ->
                            if (selectedTabIndex < tabPositions.size) {
                                TabRowDefaults.SecondaryIndicator(
                                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                    height = 2.dp,
                                    color = contentColor
                                )
                            }
                        }
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = {
                                    Text(
                                        text = title,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp)
                    ) {
                        items(uiState.forecastList) { forecast ->
                            val (displayValue, displayIcon) = when (selectedTabIndex) {
                                0 -> "${forecast.temp}°" to getWeatherIcon(forecast.iconCode)
                                1 -> "${forecast.windSpeed} km/h" to Icons.Default.Air
                                2 -> "${forecast.precipChance}%" to Icons.Default.WaterDrop
                                else -> "${forecast.temp}°" to getWeatherIcon(forecast.iconCode)
                            }

                            ForecastItemView(
                                time = forecast.time,
                                value = displayValue,
                                icon = displayIcon
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}