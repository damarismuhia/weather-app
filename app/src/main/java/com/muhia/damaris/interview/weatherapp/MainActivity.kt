package com.muhia.damaris.interview.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.muhia.damaris.interview.weatherapp.ui.nav.WeatherAppNavigation
import com.muhia.damaris.interview.weatherapp.ui.theme.WeatherAppTheme
import com.muhia.damaris.interview.weatherapp.viewmodel.WeatherViewModel
import com.scottyab.rootbeer.RootBeer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var rootBeer: RootBeer

    private val mainViewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherAppNavigation(viewModel = mainViewModel, navController = rememberNavController())
                }
            }
        }
    }

}
