package com.shihab.weather_app_compose.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.shihab.weather_app_compose.R

fun getWeatherAnimation(iconCode: String): Int {
    return when (iconCode) {
        "01d", "01n" -> MaterialTheme.i
        "02d", "02n", "03d", "03n", "04d", "04n" -> R.raw.cloudy
        "09d", "09n", "10d", "10n" -> R.raw.rain
        "11d", "11n" -> R.raw.storm
        "13d", "13n" -> R.raw.snow
        else -> R.raw.sunny
    }
}

fun getDynamicGradient(iconCode: String): Brush {
    val colors = when (iconCode) {
        "01d" -> listOf(Color(0xFF29B6F6), Color(0xFF4FC3F7))
        "01n", "02n", "03n", "04n", "09n", "10n", "11n" -> listOf(Color(0xFF0D47A1), Color(0xFF000000))
        "02d", "03d", "04d" -> listOf(Color(0xFF607D8B), Color(0xFF90A4AE))
        "09d", "10d" -> listOf(Color(0xFF37474F), Color(0xFF546E7A))
        "11d" -> listOf(Color(0xFF4527A0), Color(0xFF311B92))
        else -> listOf(Color(0xFF29B6F6), Color(0xFF4FC3F7))
    }

    return Brush.verticalGradient(colors = colors)
}