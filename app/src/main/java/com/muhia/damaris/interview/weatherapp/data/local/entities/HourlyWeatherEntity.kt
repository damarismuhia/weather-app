package com.muhia.damaris.interview.weatherapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hourly_weather")
data class HourlyWeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weatherDate: String,  // Foreign key reference to WeatherEntity
    val temperatureC: Double,
    val temperatureF: Double,
    val time: String,
    val icon: String
)