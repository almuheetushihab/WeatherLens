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
    val wind: WindData,
    @SerializedName("visibility")
    val visibility: Int
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
    val weather: List<WeatherDescription>,
    @SerializedName("wind")
    val wind: WindData,
    @SerializedName("pop")
    val pop: Double
)

data class MainData(
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("pressure")
    val pressure: Int,
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