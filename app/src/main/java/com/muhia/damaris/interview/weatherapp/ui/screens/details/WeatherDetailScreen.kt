package com.muhia.damaris.interview.weatherapp.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.muhia.damaris.interview.weatherapp.ui.components.WeatherErrorState
import com.muhia.damaris.interview.weatherapp.utils.ForecastWeatherUiState
import com.muhia.damaris.interview.weatherapp.viewmodel.WeatherViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailScreen(viewModel: WeatherViewModel,
                        date: String, navController: NavHostController) {
    val hourlyWeatherState by viewModel.hourlyWeatherState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchWeatherForDate(date = date)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                WeatherDetailContent(
                    uiState = hourlyWeatherState,
                    date = date,
                    viewModel = viewModel
                )
            }
        }
    )
}
@Composable
fun WeatherDetailContent(
    uiState: ForecastWeatherUiState,
    date: String,
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
            Timber.e("HOUR:: ${uiState.errorMessage}")
            WeatherErrorState(error = uiState.errorMessage) {
                viewModel.fetchWeatherForDate(date)
            }
        }

        else -> {
            if (uiState.dayWeather != null) {
                WeatherForecast(state = uiState)
            } else {
                WeatherErrorState(error = "No weather data available") {
                    viewModel.fetchWeatherForDate(date)
                }
            }
        }
    }
}