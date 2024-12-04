package com.muhia.damaris.interview.weatherapp.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.muhia.damaris.interview.weatherapp.R
import com.muhia.damaris.interview.weatherapp.data.model.ForecastDayData
import com.muhia.damaris.interview.weatherapp.ui.components.ConditionsLabelSection
import com.muhia.damaris.interview.weatherapp.ui.components.RadioButtonComponent
import com.muhia.damaris.interview.weatherapp.utils.DEFAULT_WEATHER_DESTINATION
import com.muhia.damaris.interview.weatherapp.utils.Factory.formatDate
import com.muhia.damaris.interview.weatherapp.utils.ForecastWeatherUiState
import com.muhia.damaris.interview.weatherapp.utils.TemperatureUnit
import com.muhia.damaris.interview.weatherapp.utils.formatHumidityValue
import com.muhia.damaris.interview.weatherapp.utils.formatMamMinTemp
import com.muhia.damaris.interview.weatherapp.utils.formatWindValue

@Composable
fun WeatherForecast(
    state: ForecastWeatherUiState,
) {
    var isCelsius by remember { mutableStateOf(true) }
   if ( state.dayWeather != null) {
       Column(
           modifier = Modifier
               .fillMaxSize()
               .verticalScroll(rememberScrollState())
       ) {
           DayWeatherDetailsComponent(
               data = state.dayWeather,
               isCelsius = isCelsius,
               onUnitSelected = { selectedUnit ->
                   isCelsius = selectedUnit
               }
           )
           Spacer(modifier = Modifier.height(16.dp))
           HourlyComponent(state = state, isCelsius =isCelsius)
       }
   }
}


@Composable
fun DayWeatherDetailsComponent(
    data: ForecastDayData,
    isCelsius: Boolean,
    onUnitSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        Text(
            text = DEFAULT_WEATHER_DESTINATION,
            style = MaterialTheme.typography.titleLarge,
            color =  MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = data.date.formatDate("yyyy-MM-dd","MMM d, yyyy"),
            style = MaterialTheme.typography.bodyMedium,
            color =  MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = modifier.height(8.dp))
        RadioButtonComponent(isCelsius, onUnitSelected, modifier)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = formatMamMinTemp(
                maxTemp = if (isCelsius) data.maxTempC else data.maxTempF,
                minTemp = if (isCelsius) data.minTempC else data.minTempF,
                unit = if (isCelsius) TemperatureUnit.CELSIUS.displayName else TemperatureUnit.FAHRENHEIT.displayName
            ),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row ( modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start){
            AsyncImage(
                modifier = Modifier.size(42.dp),
                model = stringResource(R.string.icon_image_url, data.dayIcon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.art_clear),
                placeholder = painterResource(id = R.drawable.art_clear),
                )
            Text(
                modifier = modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 5.dp),
                text = data.dayCondition,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),

            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.daily_conditions),
            style = MaterialTheme.typography.bodyLarge,
            color =  MaterialTheme.colorScheme.primary
        )
        Column(modifier = modifier.padding(horizontal = 2.dp, vertical = 2.dp)) {
            ConditionsLabelSection(modifier, R.drawable.ic_wind, R.string.wind_label, value = formatWindValue(data.dayAverageWindSpeed))
            ConditionsLabelSection(modifier, R.drawable.ic_drop, R.string.humidity_label, value = formatHumidityValue(data.humidity))
        }

    }
}
@Composable
fun HourlyComponent(
    state: ForecastWeatherUiState,
    isCelsius: Boolean,
    modifier: Modifier = Modifier
) {
    val description = if (state.dayWeather?.isFromLocal == true) {
        stringResource(id = R.string.data_not_real_time)
    } else {
        stringResource(id = R.string.real_time_data)
    }

    ElevatedCard(
        modifier = modifier
            .padding(start = 12.dp, end = 12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(containerColor =  MaterialTheme.colorScheme.primaryContainer)
    ) {
        state.dayWeather?.hour?.let { hourDataList ->
            Column(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
            ) {
                // Description Text
                Text(
                    modifier = modifier
                        .padding(top = 12.dp),
                    text = description,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    content = {
                        items(hourDataList) { weatherData ->
                            HourlyWeatherDisplay(
                                weatherData = weatherData,
                                isCelsius = isCelsius,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(horizontal = 8.dp)
                                    .padding(bottom = 8.dp)
                            )
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherDetailsScreenPreview() {
    DayWeatherDetailsComponent(data = ForecastDayData(false,"December 4, 2024","Sunny",
        "",76.0,87.0,76.0,88.0,76.0,7, emptyList()
    ), isCelsius = true , onUnitSelected = {} )
}
