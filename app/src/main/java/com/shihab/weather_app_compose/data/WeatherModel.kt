package com.shihab.weather_app_compose.data

data class CurrentWeather(
    val city: String,
    val temperature: Int, // C
    val description: String,
    val humidity: Int, // %
    val windSpeed: Double, // km/h
    val iconResId: Int // ড্রয়েবল রিসোর্স আইডি (উদাহরণ: R.drawable.ic_sun)
)

// দৈনিক পূর্বাভাসের জন্য তথ্য
data class DailyForecast(
    val day: String,
    val maxTemp: Int,
    val minTemp: Int,
    val description: String,
    val iconResId: Int
)