package com.shihab.weather_app_compose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shihab.weather_app_compose.data.RetrofitClient
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

sealed class WeatherUiState {
    object Idle : WeatherUiState()
    object Loading : WeatherUiState()
    data class Success(
        val weather: CurrentWeatherDisplay,
        val forecastList: List<ForecastDisplay>
    ) : WeatherUiState()
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

data class ForecastDisplay(
    val time: String,
    val temp: Int,
    val iconCode: String
)

class WeatherViewModel : ViewModel() {

    val weatherUiState = mutableStateOf<WeatherUiState>(WeatherUiState.Idle)

    private val API_KEY = "f690260ae6e0a1051481e9bd617569f1"

    init {
        fetchWeather("Dhaka")
    }

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            weatherUiState.value = WeatherUiState.Loading
            try {
                val currentResponse = RetrofitClient.weatherService.getCurrentWeather(city, API_KEY)

                val forecastResponse = RetrofitClient.weatherService.getForecast(city, API_KEY)

                val currentData = CurrentWeatherDisplay(
                    city = currentResponse.city,
                    temperature = currentResponse.main.temperature.roundToInt(),
                    description = currentResponse.weather.firstOrNull()?.description?.capitalize() ?: "N/A",
                    humidity = currentResponse.main.humidity,
                    windSpeed = currentResponse.wind.speed,
                    iconCode = currentResponse.weather.firstOrNull()?.iconCode ?: "01d"
                )

                val forecastList = forecastResponse.list.map { item ->
                    ForecastDisplay(
                        time = item.dateTime.substring(11, 16),
                        temp = item.main.temperature.roundToInt(),
                        iconCode = item.weather.firstOrNull()?.iconCode ?: "01d"
                    )
                }

                weatherUiState.value = WeatherUiState.Success(currentData, forecastList)

            } catch (e: Exception) {
                weatherUiState.value = WeatherUiState.Error("City not found or Network Error")
            }
        }
    }
}