package com.shihab.weather_app_compose.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import com.shihab.weather_app_compose.R

object MockData {

    fun getCurrentWeather(): CurrentWeather {
        return CurrentWeather(
            city = "Dhaka, Bangladesh",
            temperature = 32,
            description = "Mostly Sunny",
            humidity = 65,
            windSpeed = 15.5,
            iconVector = Icons.Filled.WbSunny
        )
    }

    fun getDailyForecasts(): List<DailyForecast> {
        return listOf(
            DailyForecast("Thu", 35, 25, "Hot", Icons.Filled.WbSunny),
            DailyForecast("Fri", 33, 23, "Partly Cloudy", Icons.Filled.Cloud),
            DailyForecast("Sat", 30, 22, "Rainy", Icons.Filled.WaterDrop),
            DailyForecast("Sun", 28, 20, "Moderate Rain", Icons.Filled.WaterDrop),
            DailyForecast("Mon", 31, 21, "Sunny", Icons.Filled.WbSunny)
        )
    }
}