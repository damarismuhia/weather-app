package com.muhia.damaris.interview.weatherapp.utils

import com.muhia.damaris.interview.weatherapp.data.model.CurrentWeather
import com.muhia.damaris.interview.weatherapp.data.model.ForecastDayData

data class CurrentWeatherUiState(
    val weather: CurrentWeather? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
data class ForecastWeatherUiState(
    val dayWeather: ForecastDayData? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
