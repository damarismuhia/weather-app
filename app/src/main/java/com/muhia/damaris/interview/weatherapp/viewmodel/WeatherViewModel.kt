package com.muhia.damaris.interview.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhia.damaris.interview.weatherapp.data.model.CurrentWeather
import com.muhia.damaris.interview.weatherapp.data.model.ForecastDayData
import com.muhia.damaris.interview.weatherapp.data.repo.WeatherRepositoryImpl
import com.muhia.damaris.interview.weatherapp.utils.ApiResponse
import com.muhia.damaris.interview.weatherapp.utils.CurrentWeatherUiState
import com.muhia.damaris.interview.weatherapp.utils.ForecastWeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val remoteWeatherRepository:
                                           WeatherRepositoryImpl): ViewModel() {



    private val _currentWeatherDetails = MutableStateFlow(CurrentWeatherUiState())
    val currentWeatherDetails: StateFlow<CurrentWeatherUiState> = _currentWeatherDetails.asStateFlow() //immutable



    private val _hourlyWeatherState = MutableStateFlow(ForecastWeatherUiState())
    val hourlyWeatherState: StateFlow<ForecastWeatherUiState> = _hourlyWeatherState.asStateFlow() //immutable

    fun fetchCurrentWeatherData() {
        viewModelScope.launch {
            _currentWeatherDetails.emit(CurrentWeatherUiState(isLoading = true))
           val result = remoteWeatherRepository.getCurrentWeatherDetails()
            _currentWeatherDetails.emit(processCurrentWeatherResult(result))
        }
    }
    fun fetchWeatherForDate(date: String) {
        viewModelScope.launch {
            _hourlyWeatherState.emit(ForecastWeatherUiState(isLoading = true))
           val result = remoteWeatherRepository.getWeatherForDate(date)
            _hourlyWeatherState.emit(processHourlyWeatherResult(result))
        }
    }

    // Process the result and emit the corresponding state
    private fun processHourlyWeatherResult(result: ApiResponse<ForecastDayData>): ForecastWeatherUiState {
        return when (result) {
            is ApiResponse.Success -> {
                ForecastWeatherUiState(
                    result.data,false,null
                )
            }
            is ApiResponse.Error -> {
                ForecastWeatherUiState(
                    null,false,result.message
                )
            }
        }
    }
    private fun processCurrentWeatherResult(result: ApiResponse<CurrentWeather>): CurrentWeatherUiState {
        return when (result) {
            is ApiResponse.Success -> {
                CurrentWeatherUiState(
                    result.data,false,null
                )
            }
            is ApiResponse.Error -> {
                Timber.e("ERROR MESSAGE IS ${result.message}")
                CurrentWeatherUiState(
                    null,false,result.message
                )
            }
        }
    }


}