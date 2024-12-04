package com.muhia.damaris.interview.weatherapp.data.model

data class CurrentWeather(
    val cityName: String,
    val localtime: String,
    val country: String,
    val temperatureC: Double,
    val temperatureF: Double,
    val humidity: Int,
    val windSpeed: Double,
    val condition: String,
    val icon: String,
)
