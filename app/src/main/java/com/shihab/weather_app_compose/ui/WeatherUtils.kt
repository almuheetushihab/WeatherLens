package com.shihab.weather_app_compose.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

fun getWeatherIcon(iconCode: String): ImageVector {
    return when (iconCode) {
        "01d" -> Icons.Default.WbSunny
        "01n" -> Icons.Outlined.WbSunny
        "02d", "02n", "03d", "03n", "04d", "04n" -> Icons.Default.Cloud
        "09d", "09n", "10d", "10n" -> Icons.Default.WaterDrop
        "11d", "11n" -> Icons.Default.FlashOn
        "13d", "13n" -> Icons.Default.AcUnit
        "50d", "50n" -> Icons.Default.Menu
        else -> Icons.Default.WbSunny
    }
}

fun getDynamicGradient(iconCode: String): Brush {
    val colors = when (iconCode) {
        "01d" -> listOf(Color(0xFF4FC3F7), Color(0xFF29B6F6), Color(0xFF0288D1))
        "01n", "02n", "03n", "04n", "09n", "10n", "11n", "13n", "50n" -> listOf(
            Color(0xFF1A237E),
            Color(0xFF283593),
            Color(0xFF3949AB)
        )

        "02d", "03d", "04d", "50d" -> listOf(
            Color(0xFF90A4AE),
            Color(0xFF78909C),
            Color(0xFF607D8B)
        )

        "09d", "10d", "11d" -> listOf(Color(0xFF546E7A), Color(0xFF455A64), Color(0xFF37474F))
        "13d" -> listOf(Color(0xFFB3E5FC), Color(0xFF81D4FA), Color(0xFF4FC3F7))
        else -> listOf(Color(0xFF4FC3F7), Color(0xFF29B6F6), Color(0xFF0288D1))
    }
    return Brush.verticalGradient(colors = colors)
}

fun getPressureIcon(): ImageVector = Icons.Default.Speed
fun getVisibilityIcon(): ImageVector = Icons.Default.Visibility
fun getWindIcon(): ImageVector = Icons.Default.Air
fun getHumidityIcon(): ImageVector = Icons.Default.WaterDrop