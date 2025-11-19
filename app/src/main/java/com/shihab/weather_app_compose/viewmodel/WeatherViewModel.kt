package com.shihab.weather_app_compose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.shihab.weather_app_compose.data.MockData

class WeatherViewModel : ViewModel() {

    val currentWeatherState = mutableStateOf(MockData.getCurrentWeather())
    val forecastListState = mutableStateOf(MockData.getDailyForecasts())

    init {
        loadWeatherData()
    }

    private fun loadWeatherData() {
        currentWeatherState.value = MockData.getCurrentWeather()
        forecastListState.value = MockData.getDailyForecasts()
    }
}