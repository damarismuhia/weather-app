package com.muhia.damaris.interview.weatherapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey val date: String,
    val dayCondition: String,
    val dayIcon: String,
    val maxTempC: Double,
    val minTempC: Double,
    val maxTempF: Double,
    val minTempF: Double,
    val dayAverageWindSpeed: Double,
    val humidity: Int,
)



