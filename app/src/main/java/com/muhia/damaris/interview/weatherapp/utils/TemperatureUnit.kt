package com.muhia.damaris.interview.weatherapp.utils

enum class TemperatureUnit(val symbol: String, val displayName: String) {
    CELSIUS("°C", "Celsius"),
    FAHRENHEIT("°F", "Fahrenheit");

    override fun toString(): String {
        return "$displayName ($symbol)"
    }
}
