package com.shihab.weather_app_compose.data

import androidx.compose.ui.graphics.vector.ImageVector

data class CurrentWeather(
    val city: String,
    val temperature: Int,
    val description: String,
    val humidity: Int,
    val windSpeed: Double,
    val iconVector: ImageVector
)


data class DailyForecast(
    val day: String,
    val maxTemp: Int,
    val minTemp: Int,
    val description: String,
    val iconVector: ImageVector
)