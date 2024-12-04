package com.muhia.damaris.interview.weatherapp.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.muhia.damaris.interview.weatherapp.R
import com.muhia.damaris.interview.weatherapp.ui.components.RadioButtonComponent
import com.muhia.damaris.interview.weatherapp.ui.components.WeatherErrorState
import com.muhia.damaris.interview.weatherapp.ui.screens.calendar.CalendarView
import com.muhia.damaris.interview.weatherapp.utils.CurrentWeatherUiState
import com.muhia.damaris.interview.weatherapp.utils.getDatesForMonth
import com.muhia.damaris.interview.weatherapp.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun WeatherHomeScreen(viewModel: WeatherViewModel, navController: NavController) {
    // State to hold the current month and year
    val calendarState = remember { mutableStateOf(Calendar.getInstance()) }
    val currentWeatherState by viewModel.currentWeatherDetails.collectAsState()

    // State for temperature unit (Celsius/Fahrenheit)
    val isCelsius = remember { mutableStateOf(true) }

    val onUnitSelected: (Boolean) -> Unit = { selectedCelsius ->
        isCelsius.value = selectedCelsius
    }

    LaunchedEffect(Unit) {
        viewModel.fetchCurrentWeatherData()
    }

    val datesForCurrentMonth = getDatesForMonth(calendarState.value)

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(
            rememberScrollState()
        )
        .padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))

        // Display the formatted month and year
        Text(
            text = stringResource(R.string.select_a_date_from_the_calendar_to_view_the_weather_details),
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(8.dp))
        // CalendarView
        CalendarView(
            month = calendarState.value.time,
            date = datesForCurrentMonth,
            displayNext = true,
            displayPrev = true,
            onClickNext = {
                val updatedCalendar = calendarState.value.clone() as Calendar
                updatedCalendar.add(Calendar.MONTH, 1) // Move to next month
                calendarState.value = updatedCalendar
            },
            onClickPrev = {
                val updatedCalendar = calendarState.value.clone() as Calendar
                updatedCalendar.add(Calendar.MONTH, -1) // Move to previous month
                calendarState.value = updatedCalendar
            },
            onClick = { date ->
                val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
                navController.navigate("weatherDetail/$formattedDate")
            },
            startFromSunday = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        WeatherScreenContent(uiState = currentWeatherState, isCelsius = isCelsius.value, onUnitSelected = onUnitSelected, viewModel = viewModel )
    }

}
@Composable
fun WeatherScreenContent(
    uiState: CurrentWeatherUiState,
    isCelsius: Boolean,
    onUnitSelected: (Boolean) -> Unit,
    viewModel: WeatherViewModel,
) {
    when {
        uiState.isLoading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }

        uiState.errorMessage != null -> {
            WeatherErrorState(error = uiState.errorMessage) {
                viewModel.fetchCurrentWeatherData()
            }
        }

        else -> {
            if (uiState.weather != null) {
                WeatherSuccessState(uiState = uiState, isCelsius = isCelsius,onUnitSelected)
            } else {
                WeatherErrorState(error = "No weather data available") {
                    viewModel.fetchCurrentWeatherData()
                }
            }
        }
    }
}
@Composable
fun WeatherSuccessState( uiState: CurrentWeatherUiState,
                         isCelsius: Boolean,onUnitSelected: (Boolean) -> Unit){
    Column(modifier = Modifier
        .fillMaxWidth()) {
        RadioButtonComponent(isCelsius, onUnitSelected)
        CurrentWeatherScreen(uiState,  MaterialTheme.colorScheme.primaryContainer,isCelsius)
    }

}

