package com.example.practice2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practice2.ui.theme.Practice2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practice2Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    QuizApp()
                }
            }
        }
    }
}

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

@Composable
fun StartScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { navController.navigate("quiz") }) {
            Text("クイズスタート")
        }
    }
}

@Composable
fun QuizScreen(navController: NavHostController) {
    val questions = listOf(
        Question(
            question = "What is the national sport of Japan?",
            options = listOf("Sumo", "Soccer", "Baseball", "Tennis"),
            correctAnswer = "Sumo"
        ),
        Question(
            question = "Which sport is known as the 'king of sports'?",
            options = listOf("Football", "Basketball", "Tennis", "Cricket"),
            correctAnswer = "Football"
        )
    )

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var totalCorrectAnswers by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var answerSubmitted by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val question = questions[currentQuestionIndex]
        Text(text = "Question ${currentQuestionIndex + 1} of ${questions.size}", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = question.question, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        question.options.forEach { option ->
            Button(
                onClick = {
                    if (!answerSubmitted) {
                        selectedAnswer = option
                        answerSubmitted = true
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (option == selectedAnswer) {
                        if (option == question.correctAnswer) Color.Green else Color.Red
                    } else if (option == question.correctAnswer && answerSubmitted) {
                        Color.Green
                    } else {
                        Color.Gray
                    }
                )
            ) {
                Text(text = option)
            }
        }
        if (answerSubmitted) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (selectedAnswer == question.correctAnswer) {
                        totalCorrectAnswers++
                    }
                    if (currentQuestionIndex < questions.size - 1) {
                        currentQuestionIndex++
                        answerSubmitted = false
                        selectedAnswer = null
                    } else {
                        navController.navigate("result/${totalCorrectAnswers}/${questions.size}")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun ResultScreen(navController: NavHostController, score: Int, total: Int) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Quiz Completed!", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
        Text(text = "Your score: $score out of $total (${(score.toFloat() / total * 100).toInt()}%)", style = MaterialTheme.typography.bodyLarge)
        Button(onClick = { navController.navigate("start") }) {
            Text("Return to Start")
        }
    }
}

data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Practice2Theme {
        QuizApp()
    }
}