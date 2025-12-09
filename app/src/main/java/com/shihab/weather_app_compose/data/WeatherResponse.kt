package com.shihab.weather_app_compose.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("name")
    val city: String,
    @SerializedName("main")
    val main: MainData,
    @SerializedName("weather")
    val weather: List<WeatherDescription>,
    @SerializedName("wind")
    val wind: WindData
)

data class ForecastResponse(
    @SerializedName("list")
    val list: List<ForecastItemData>
)

data class ForecastItemData(
    @SerializedName("dt_txt")
    val dateTime: String,
    @SerializedName("main")
    val main: MainData,
    @SerializedName("weather")
    val weather: List<WeatherDescription>
)

data class MainData(
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("humidity")
    val humidity: Int,
)

data class WeatherDescription(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val iconCode: String
)

data class WindData(
    @SerializedName("speed")
    val speed: Double
)