package com.muhia.damaris.interview.weatherapp.utils

import com.muhia.damaris.interview.weatherapp.data.local.entities.HourlyWeatherEntity
import com.muhia.damaris.interview.weatherapp.data.local.entities.WeatherEntity
import com.muhia.damaris.interview.weatherapp.data.local.entities.WeatherWithHourlyWeather
import com.muhia.damaris.interview.weatherapp.data.model.CurrentWeather
import com.muhia.damaris.interview.weatherapp.data.model.ForecastDayData
import com.muhia.damaris.interview.weatherapp.data.model.ForecastHourlyWeather
import com.muhia.damaris.interview.weatherapp.data.model.ForecastResponse
import com.muhia.damaris.interview.weatherapp.data.model.WeatherResponse
import com.muhia.damaris.interview.weatherapp.data.network.NoNetworkException
import okhttp3.internal.http2.ConnectionShutdownException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import kotlin.math.max
import kotlin.math.roundToInt

fun WeatherResponse.mapToCurrentWeather(): CurrentWeather {
    return CurrentWeather(
        cityName = location.name,
        localtime = location.localtime,
        country = location.country,
        temperatureC = current.tempC,
        temperatureF = current.tempF,
        humidity = current.humidity,
        windSpeed = current.windKph,
        condition = current.condition.text,
        icon = current.condition.icon
    )
}
fun ForecastResponse.mapDayForecastWeather(): ForecastDayData? {

    // Safely get the first element of forecastday or return null if empty
    val forecastDay = this.forecast.forecastday.firstOrNull()
        ?: return null

    return ForecastDayData(
        date = forecastDay.date,
        dayCondition = forecastDay.day.condition.text,
        dayIcon = forecastDay.day.condition.icon,
        maxTempC = forecastDay.day.maxtempC,
        minTempC = forecastDay.day.mintempC,
        maxTempF = forecastDay.day.maxtempF,
        minTempF = forecastDay.day.mintempF,
        dayAverageWindSpeed = forecastDay.day.maxwindKph,
        humidity = forecastDay.day.avghumidity,

        hour = forecastDay.hour.map { hourly ->
            ForecastHourlyWeather(
                temperatureC = hourly.tempC,
                temperatureF = hourly.tempF,
                time = hourly.time,
                icon = hourly.condition.icon
            )
        }
    )
}
fun ForecastDayData.mapWeatherEntity(date: String): WeatherEntity {
   return WeatherEntity(
        date = date,
        dayCondition = dayCondition,
       dayIcon = dayIcon,
       maxTempC = maxTempC,
       minTempC = minTempC,
       maxTempF = maxTempF,
       minTempF = minTempF,
        dayAverageWindSpeed = dayAverageWindSpeed,
        humidity = humidity
    )
}
fun ForecastDayData.mapHourlyWeatherEntity(): List<HourlyWeatherEntity> {
    val hourlyWeatherEntities = hour.map {
        HourlyWeatherEntity(
            weatherDate = date,
            temperatureC = it.temperatureC,
            temperatureF = it.temperatureF,
            time = it.time,
            icon = it.icon
        )
    }
    return hourlyWeatherEntities
}
fun WeatherWithHourlyWeather.toForecastDayData(): ForecastDayData {
    return ForecastDayData(
        date = weather.date,
        dayCondition = weather.dayCondition,
        dayIcon = weather.dayIcon,
        maxTempC = weather.maxTempC,
        minTempC = weather.minTempC,
        maxTempF = weather.maxTempF,
        minTempF = weather.minTempF,
        dayAverageWindSpeed = weather.dayAverageWindSpeed,
        humidity = weather.humidity,
        hour = hourlyWeather.map { hourly ->
            ForecastHourlyWeather(
                temperatureC = hourly.temperatureC,
                temperatureF = hourly.temperatureF,
                time = hourly.time,
                icon = hourly.icon
            )
        }
    )
}
fun formatTemperatureValue(temperature: Double, unit: String): String =
    "${temperature.roundToInt()}${getUnitSymbols(unit = unit)}"


fun formatMamMinTemp(
    maxTemp: Double,
    minTemp: Double,
    unit: String
): String {
    return "${ formatTemperatureValue(maxTemp,unit)} / ${ formatTemperatureValue(minTemp,unit)}"
}


private fun getUnitSymbols(unit: String) = when (unit) {
    TemperatureUnit.CELSIUS.displayName -> TemperatureUnit.CELSIUS.symbol
    TemperatureUnit.FAHRENHEIT.displayName -> TemperatureUnit.FAHRENHEIT.symbol
    else -> "N/A"
}

fun formatWindValue(speed: Double): String =
    "${speed.roundToInt()} ${Constants.windSpeedUnit}"

fun formatHumidityValue(humidity: Int): String =
    "$humidity ${Constants.percentSymbol}"
fun resolveException(e: Exception): String {
    val message = "Something went wrong.Please try again later."
    when (e) {
        is NoNetworkException ->{
            return "Please check your internet and try again later"
        }
        is SocketTimeoutException -> {
            return e.message ?: "An error occurred while trying to connect to the service. Please try again later"
        }
        is ConnectException ->{
            return "Please check your internet and try again later"
        }
        is SocketException -> {
            return "Socket error occurred.Please check your internet and try again later."
        }
        is ConnectionShutdownException -> {
            return "Connection shutdown. Please check your internet and try again later"
        }
        is UnknownHostException, is SSLHandshakeException -> {
            return "Unable to connect to server.Please try again later."
        }
    }
    if (e is HttpException) {
        val errorResponseJson = e.response()?.errorBody()?.string()
        if (!errorResponseJson.isNullOrEmpty()) {
            return try {
                val errorJson = JSONObject(errorResponseJson)
                val nestedError = errorJson.optJSONObject("error")
                nestedError?.optString("message", message) ?: message
            } catch (ex: Exception) {
                message
            }
        }
    }

    return message
}


