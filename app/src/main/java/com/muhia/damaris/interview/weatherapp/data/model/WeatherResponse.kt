package com.muhia.damaris.interview.weatherapp.data.model
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current")
    val current: Weather,
    @SerializedName("location")
    val location: Location
)

data class Location(
    @SerializedName("country")
    val country: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("localtime")
    val localtime: String,
)

data class Weather(
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_mph")
    val windMph: Double,
    @SerializedName("condition")
    val condition: Condition,
)

data class Condition(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("text")
    val text: String,
)


