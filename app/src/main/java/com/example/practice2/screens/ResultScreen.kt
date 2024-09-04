package com.example.practice2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ResultScreen(navController: NavHostController, score: Int, total: Int) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Quiz Completed!", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Your score: $score out of $total (${(score.toFloat() / total * 100).toInt()}%)", style = MaterialTheme.typography.bodyLarge)
        Button(onClick = { navController.navigate("start") }) {
            Text("Return to Start")
        }
    }
}