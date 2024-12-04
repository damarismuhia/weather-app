package com.muhia.damaris.interview.weatherapp.data
import com.muhia.damaris.interview.weatherapp.data.local.entities.WeatherEntity
import com.muhia.damaris.interview.weatherapp.data.local.entities.WeatherWithHourlyWeather
import com.muhia.damaris.interview.weatherapp.data.model.Condition
import com.muhia.damaris.interview.weatherapp.data.model.DayData
import com.muhia.damaris.interview.weatherapp.data.model.Forecast
import com.muhia.damaris.interview.weatherapp.data.model.ForecastDay
import com.muhia.damaris.interview.weatherapp.data.model.ForecastDayData
import com.muhia.damaris.interview.weatherapp.data.model.ForecastHourlyWeather
import com.muhia.damaris.interview.weatherapp.data.model.ForecastResponse
import com.muhia.damaris.interview.weatherapp.data.model.HourlyWeather
import com.muhia.damaris.interview.weatherapp.data.model.Location
import com.muhia.damaris.interview.weatherapp.data.model.Weather
import com.muhia.damaris.interview.weatherapp.data.model.WeatherCondition
import com.muhia.damaris.interview.weatherapp.data.model.WeatherResponse

val weatherResponse : WeatherResponse
    get() = WeatherResponse(current = Weather(19.0,50.0,55,34.9,
        89.9, condition = Condition("sunny-icon","Sunny")
    ), location = Location("Kenya","Nairobi","2024-12-04 12:00:00"))


var weatherWithHourlyWeather = WeatherWithHourlyWeather(weather = WeatherEntity(date = "2024-12-04",
    "Sunny","icon",19.0,50.0,55.8,34.9,
    56.6,76), hourlyWeather = emptyList()
)

val forecastResponse: ForecastResponse
    get() = ForecastResponse(
        forecast = Forecast(
            forecastday = listOf(
                ForecastDay(
                    date = "2024-12-04",
                    day = DayData(
                        avghumidity = 65,
                        maxtempC = 28.0,
                        maxtempF = 82.4,
                        maxwindKph = 18.0,
                        maxwindMph = 11.2,
                        mintempC = 18.0,
                        mintempF = 64.4,
                        condition = WeatherCondition(
                            text = "Sunny",
                            icon = "sunny-icon"
                        )
                    ),
                    hour = listOf(
                        HourlyWeather(
                            tempC = 20.0,
                            tempF = 68.0,
                            time = "2024-12-04 01:00",
                            humidity = 85,
                            windKph = 8.0,
                            windMph = 5.0,
                            condition = WeatherCondition(
                                text = "Sunny",
                                icon = "sunny-icon"
                            )
                        )
                    )
                )
            )
        )
    )
val sampleForecastDayData: ForecastDayData
    get() = ForecastDayData(
        isFromLocal = false,
        date = "2024-12-04",
        dayCondition = "Sunny",
        dayIcon = "sunny-icon",
        maxTempC = 28.0,
        minTempC = 18.0,
        maxTempF = 82.4,
        minTempF = 64.4,
        dayAverageWindSpeed = 15.0,
        humidity = 60,
        hour = listOf(
            ForecastHourlyWeather(
                temperatureC = 20.0,
                temperatureF = 68.0,
                time = "2024-12-04 01:00",
                icon = "sunny-icon"
            )
        )
    )


