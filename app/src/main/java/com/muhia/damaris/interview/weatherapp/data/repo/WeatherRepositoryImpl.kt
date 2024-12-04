package com.muhia.damaris.interview.weatherapp.data.repo

import com.muhia.damaris.interview.weatherapp.data.local.room.WeatherDao
import com.muhia.damaris.interview.weatherapp.data.model.CurrentWeather
import com.muhia.damaris.interview.weatherapp.data.model.ForecastDayData
import com.muhia.damaris.interview.weatherapp.data.network.WeatherApiService
import com.muhia.damaris.interview.weatherapp.utils.ApiResponse
import com.muhia.damaris.interview.weatherapp.utils.Constants
import com.muhia.damaris.interview.weatherapp.utils.DEFAULT_WEATHER_DESTINATION
import com.muhia.damaris.interview.weatherapp.utils.mapDayForecastWeather
import com.muhia.damaris.interview.weatherapp.utils.mapHourlyWeatherEntity
import com.muhia.damaris.interview.weatherapp.utils.mapToCurrentWeather
import com.muhia.damaris.interview.weatherapp.utils.mapWeatherEntity
import com.muhia.damaris.interview.weatherapp.utils.resolveException
import com.muhia.damaris.interview.weatherapp.utils.toForecastDayData
import timber.log.Timber
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherApiService: WeatherApiService,
                                                private val weatherDao: WeatherDao
): WeatherRepository {
    override suspend fun getCurrentWeatherDetails(): ApiResponse<CurrentWeather> {
        return try {
            val response = weatherApiService.getCurrentWeather(DEFAULT_WEATHER_DESTINATION,Constants.apiKey)
            val currentWeather = response.mapToCurrentWeather()
            ApiResponse.Success(currentWeather)
        }catch (e: Exception) {

            ApiResponse.Error(resolveException(e))
        }

    }

    override suspend fun getWeatherForDate(date: String): ApiResponse<ForecastDayData> {
        return try {
            val cachedData = weatherDao.getWeatherForDate(date)
            if (cachedData != null) {
                Timber.e("FROM DB: ")
                val data = cachedData.toForecastDayData()
                data.isFromLocal = true
                ApiResponse.Success(data)
            } else {
                Timber.e("FROM REMOTE")
                val response = weatherApiService.getWeatherForDate(Constants.apiKey,
                    DEFAULT_WEATHER_DESTINATION,date)
                val weatherData = response.mapDayForecastWeather()

                if (weatherData != null){
                    weatherDao.insertWeather(weatherData.mapWeatherEntity(date))
                    weatherDao.insertHourlyWeather(weatherData.mapHourlyWeatherEntity())
                }
                weatherData?.isFromLocal = false
                ApiResponse.Success(weatherData)
            }
        }catch (e: Exception) {
            ApiResponse.Error(resolveException(e))
        }
    }


}