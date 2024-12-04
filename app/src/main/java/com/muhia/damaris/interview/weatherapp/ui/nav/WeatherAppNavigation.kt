package com.muhia.damaris.interview.weatherapp.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muhia.damaris.interview.weatherapp.ui.screens.details.WeatherDetailScreen
import com.muhia.damaris.interview.weatherapp.ui.screens.home.WeatherHomeScreen
import com.muhia.damaris.interview.weatherapp.viewmodel.WeatherViewModel


@Composable
fun WeatherAppNavigation(
    viewModel: WeatherViewModel,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            WeatherHomeScreen(viewModel = viewModel, navController = navController)
        }
        composable("weatherDetail/{date}") { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""
            WeatherDetailScreen(viewModel = viewModel, date = date, navController = navController)
        }
    }
}
