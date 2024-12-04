package com.muhia.damaris.interview.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhia.damaris.interview.weatherapp.data.local.entities.HourlyWeatherEntity
import com.muhia.damaris.interview.weatherapp.data.local.entities.WeatherEntity
import com.muhia.damaris.interview.weatherapp.data.local.room.WeatherDao

@Database(entities = [WeatherEntity::class, HourlyWeatherEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}



