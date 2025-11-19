package com.shihab.weather_app_compose.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shihab.weather_app_compose.data.*
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val weather: CurrentWeatherDisplay) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

data class CurrentWeatherDisplay(
    val city: String,
    val temperature: Int,
    val description: String,
    val humidity: Int,
    val windSpeed: Double,
    val iconCode: String
)

class WeatherViewModel : ViewModel() {

    val weatherUiState = mutableStateOf<WeatherUiState>(WeatherUiState.Loading)

    private val API_KEY = "f690260ae6e0a1051481e9bd617569f1"

    init {
        loadWeather(lat = 23.777176, lon = 90.399452)
    }

    fun loadWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            weatherUiState.value = WeatherUiState.Loading
            try {
                val response = RetrofitClient.weatherService.getCurrentWeather(
                    lat = lat,
                    lon = lon,
                    apiKey = API_KEY
                )

                val displayData = CurrentWeatherDisplay(
                    city = response.city,
                    temperature = response.main.temperature.roundToInt(),
                    description = response.weather.firstOrNull()?.description ?: "N/A",
                    humidity = response.main.humidity,
                    windSpeed = 0.0,
                    iconCode = response.weather.firstOrNull()?.iconCode ?: "01d"
                )

                weatherUiState.value = WeatherUiState.Success(displayData)
                Log.d("WeatherViewModel", "Weather loaded successfully${response.weather}----${response.main}---${response.city} ")

            } catch (e: Exception) {
                weatherUiState.value = WeatherUiState.Error("Failed to fetch weather: ${e.message}")
            }
        }
    }
}