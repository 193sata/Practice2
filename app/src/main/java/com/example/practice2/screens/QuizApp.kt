package com.example.practice2.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
//import androidx.navigation.NavHostController

@Composable
fun QuizApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "start") {
        composable("start") {
            StartScreen(navController)
        }
        composable("quiz") {
            QuizScreen(navController)
        }
        composable("result/{score}/{total}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toInt() ?: 0
            val total = backStackEntry.arguments?.getString("total")?.toInt() ?: 0
            ResultScreen(navController, score, total)
        }
    }
}