package com.muhia.damaris.interview.weatherapp.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class WeatherWithHourlyWeather(
    @Embedded val weather: WeatherEntity,
    @Relation(
        parentColumn = "date",
        entityColumn = "weatherDate"
    )
    val hourlyWeather: List<HourlyWeatherEntity>
)