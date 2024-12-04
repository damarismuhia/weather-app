package com.muhia.damaris.interview.weatherapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.muhia.damaris.interview.weatherapp.R
import com.muhia.damaris.interview.weatherapp.ui.components.ConditionsLabelSection
import com.muhia.damaris.interview.weatherapp.utils.CurrentWeatherUiState
import com.muhia.damaris.interview.weatherapp.utils.Factory.formatDate
import com.muhia.damaris.interview.weatherapp.utils.TemperatureUnit
import com.muhia.damaris.interview.weatherapp.utils.formatHumidityValue
import com.muhia.damaris.interview.weatherapp.utils.formatTemperatureValue
import com.muhia.damaris.interview.weatherapp.utils.formatWindValue

@Composable
fun CurrentWeatherScreen(
    state: CurrentWeatherUiState,
    backgroundColor: Color =  MaterialTheme.colorScheme.primaryContainer,
    isCelsius: Boolean,
    modifier: Modifier = Modifier
){
    state.weather.let { data ->
       if (data != null)  {
           Card(
               shape = RoundedCornerShape(10.dp),
               colors = CardDefaults.cardColors(containerColor = backgroundColor)
           ){
               Column(
                   modifier = modifier
                       .fillMaxWidth()
                       .padding(16.dp),
               ) {
                   Text(
                       text = "Current Date & Time: ${data.localtime.formatDate("yyyy-MM-dd HH:mm", "MMM d, yyyy | hh:mm a")}",
                       style = MaterialTheme.typography.bodyMedium,
                       color =  MaterialTheme.colorScheme.onSurface
                   )

                   Spacer(modifier = modifier.height(8.dp))

                   Text(
                       text = formatTemperatureValue(
                           temperature = if (isCelsius) data.temperatureC else data.temperatureF,
                           unit = if (isCelsius) TemperatureUnit.CELSIUS.displayName else TemperatureUnit.FAHRENHEIT.displayName
                       ),
                       style = MaterialTheme.typography.displaySmall,
                       color = MaterialTheme.colorScheme.onBackground
                   )
                   Row ( modifier = modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.Start){
                       AsyncImage(
                           modifier = Modifier.size(42.dp),
                           model = stringResource(R.string.icon_image_url, data.icon),
                           contentDescription = null,
                           contentScale = ContentScale.Crop,
                           error = painterResource(id = R.drawable.art_clear),
                           placeholder = painterResource(id = R.drawable.art_clear),
                       )
                       Text(
                           modifier = modifier
                               .align(Alignment.CenterVertically)
                               .padding(start = 5.dp),
                           text = data.condition,
                           style = MaterialTheme.typography.bodyLarge,
                           color = MaterialTheme.colorScheme.onSurface,

                           )
                   }
                   Spacer(modifier = Modifier.height(8.dp))
                   HorizontalDivider(
                       thickness = 0.5.dp,
                       color = MaterialTheme.colorScheme.onBackground
                   )
                   Spacer(modifier = Modifier.height(8.dp))
                   Text(
                       text = stringResource(id = R.string.weather_overview),
                       style = MaterialTheme.typography.bodyLarge,
                       color =  MaterialTheme.colorScheme.primary
                   )
                   Column(modifier = modifier.padding(horizontal = 2.dp, vertical = 2.dp)) {
                       ConditionsLabelSection(modifier, R.drawable.ic_wind, R.string.wind_label, value = formatWindValue(data.windSpeed))
                       ConditionsLabelSection(modifier, R.drawable.ic_drop, R.string.humidity_label, value = formatHumidityValue(data.humidity))
                   }

               }
           }

       }
    }
}
