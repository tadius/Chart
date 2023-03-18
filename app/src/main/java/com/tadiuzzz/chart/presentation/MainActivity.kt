package com.tadiuzzz.chart.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tadiuzzz.chart.presentation.chartScreen.ChartScreen
import com.tadiuzzz.chart.presentation.pointsScreen.PointsScreen
import com.tadiuzzz.chart.presentation.util.Screen
import com.tadiuzzz.chart.ui.theme.ChartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChartTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.PointsScreen.route
                    ) {
                        composable(route = Screen.PointsScreen.route) {
                            PointsScreen(navController = navController)
                        }
                        composable(route = Screen.ChartScreen.route) {
                            ChartScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}