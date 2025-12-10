package com.shihab.weather_app_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.shihab.weather_app_compose.ui.screen.weatherScreen.WeatherScreen
import com.shihab.weather_app_compose.ui.theme.WeatherAppComposeTheme

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shihab.weather_app_compose.ui.screen.settingsScreen.SettingsScreen
import com.shihab.weather_app_compose.ui.screen.splashScreen.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppComposeTheme {
                WeatherAppNavigation()
            }
        }
    }
}

@Composable
fun WeatherAppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") { SplashScreen(navController) }

        composable("home_screen") { WeatherScreen(navController) }

        composable("settings_screen") { SettingsScreen(navController) }
    }
}