package com.shihab.weather_app_compose.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shihab.weather_app_compose.data.RetrofitClient
import com.shihab.weather_app_compose.data.SettingsStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    val weatherUiState = mutableStateOf<WeatherUiState>(WeatherUiState.Idle)
    private val API_KEY = "f690260ae6e0a1051481e9bd617569f1"
    private val settingsStore = SettingsStore(application)

    private var lastCity: String = "Dhaka"
    private var isCelsiusMode: Boolean = true

    init {
        viewModelScope.launch {
            settingsStore.isCelsius.collectLatest { isCelsius ->
                isCelsiusMode = isCelsius
                fetchWeather(lastCity)
            }
        }
    }

    fun fetchWeather(city: String) {
        lastCity = city
        viewModelScope.launch {
            weatherUiState.value = WeatherUiState.Loading
            try {
                val currentResponse = RetrofitClient.weatherService.getCurrentWeather(city, API_KEY)
                val forecastResponse = RetrofitClient.weatherService.getForecast(city, API_KEY)

                fun convert(temp: Double): Int {
                    return if (isCelsiusMode) {
                        temp.roundToInt()
                    } else {
                        ((temp * 9 / 5) + 32).roundToInt()
                    }
                }

                val unitSymbol = if (isCelsiusMode) "C" else "F"

                val visibilityKm = try {
                    "${currentResponse.visibility / 1000} km"
                } catch (e: Exception) {
                    "N/A"
                }

                val currentData = CurrentWeatherDisplay(
                    city = currentResponse.city,
                    temperature = convert(currentResponse.main.temperature),
                    tempMax = convert(currentResponse.main.tempMax),
                    tempMin = convert(currentResponse.main.tempMin),
                    feelsLike = convert(currentResponse.main.feelsLike),
                    description = currentResponse.weather.firstOrNull()?.description?.capitalize()
                        ?: "N/A",
                    humidity = currentResponse.main.humidity,
                    windSpeed = currentResponse.wind.speed,
                    pressure = currentResponse.main.pressure,
                    visibility = visibilityKm,
                    iconCode = currentResponse.weather.firstOrNull()?.iconCode ?: "01d",
                    unit = unitSymbol
                )

                val forecastList = forecastResponse.list.map { item ->
                    ForecastDisplay(
                        time = item.dateTime.substring(11, 16),
                        temp = convert(item.main.temperature),
                        iconCode = item.weather.firstOrNull()?.iconCode ?: "01d",
                        windSpeed = item.wind.speed,
                        precipChance = (item.pop * 100).toInt()
                    )
                }

                weatherUiState.value = WeatherUiState.Success(currentData, forecastList)

            } catch (e: Exception) {
                weatherUiState.value = WeatherUiState.Error("Failed: ${e.message}")
            }
        }
    }
}

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
    val tempMax: Int,
    val tempMin: Int,
    val feelsLike: Int,
    val description: String,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Int,
    val visibility: String,
    val iconCode: String,
    val unit: String
)

data class ForecastDisplay(
    val time: String,
    val temp: Int,
    val iconCode: String,
    val windSpeed: Double,
    val precipChance: Int
)