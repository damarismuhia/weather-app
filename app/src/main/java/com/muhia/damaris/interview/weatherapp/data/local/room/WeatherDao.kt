package com.muhia.damaris.interview.weatherapp.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.muhia.damaris.interview.weatherapp.data.local.entities.HourlyWeatherEntity
import com.muhia.damaris.interview.weatherapp.data.local.entities.WeatherEntity
import com.muhia.damaris.interview.weatherapp.data.local.entities.WeatherWithHourlyWeather

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyWeather(hourlyWeather: List<HourlyWeatherEntity>)

    @Transaction
    @Query("SELECT * FROM weather WHERE date = :date")
    suspend fun getWeatherForDate(date: String): WeatherWithHourlyWeather?
}

