package com.muhia.damaris.interview.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class ForecastResponse (
    val forecast:Forecast
)
data class Forecast(
    val forecastday: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    @SerializedName("day")
    val day: DayData,
    val hour: List<HourlyWeather>
)
data class DayData (
    @SerializedName("avghumidity")
    val avghumidity: Int,
    @SerializedName("maxtemp_c")
    val maxtempC: Double,
    @SerializedName("maxtemp_f")
    val maxtempF: Double,
    @SerializedName("maxwind_kph")
    val maxwindKph: Double,
    @SerializedName("maxwind_mph")
    val maxwindMph: Double,
    @SerializedName("mintemp_c")
    val mintempC: Double,
    @SerializedName("mintemp_f")
    val mintempF: Double,
    @SerializedName("condition")
    val condition: WeatherCondition,
)
data class HourlyWeather(
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    @SerializedName("time") // "2024-12-02 02:00"
    val time: String,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_mph")
    val windMph: Double,
    @SerializedName("condition")
    val condition: WeatherCondition
)

data class WeatherCondition(
    val text: String ,         // Weather condition text (e.g., "Sunny", "Light showers")
    val icon: String,
)
