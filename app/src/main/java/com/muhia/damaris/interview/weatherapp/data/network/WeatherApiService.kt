package com.muhia.damaris.interview.weatherapp.data.network

import com.muhia.damaris.interview.weatherapp.data.model.ForecastResponse
import com.muhia.damaris.interview.weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {
    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("key") apiKey: String,
    ): WeatherResponse

    @GET("forecast.json")
    suspend fun getWeatherForDate(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("dt") date: String
    ): ForecastResponse

}
