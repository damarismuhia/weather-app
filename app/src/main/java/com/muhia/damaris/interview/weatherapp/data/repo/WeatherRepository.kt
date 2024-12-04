package com.muhia.damaris.interview.weatherapp.data.repo


import com.muhia.damaris.interview.weatherapp.data.model.CurrentWeather
import com.muhia.damaris.interview.weatherapp.data.model.ForecastDayData
import com.muhia.damaris.interview.weatherapp.data.model.HourlyWeather
import com.muhia.damaris.interview.weatherapp.data.model.WeatherResponse
import com.muhia.damaris.interview.weatherapp.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
   suspend fun getCurrentWeatherDetails(): ApiResponse<CurrentWeather>
  suspend fun getWeatherForDate(date: String): ApiResponse<ForecastDayData>
}