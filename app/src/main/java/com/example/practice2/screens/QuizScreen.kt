package com.example.practice2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.practice2.models.Question

@Composable
fun QuizScreen(navController: NavHostController) {
    val questions = listOf(
        Question("What is the capital of France?", listOf("Paris", "London", "Berlin", "Madrid"), "Paris"),
        Question("Which sport is known as the 'king of sports'?", listOf("Football", "Basketball", "Tennis", "Cricket"), "Football")
    )

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var totalCorrectAnswers by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var answerSubmitted by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (currentQuestionIndex < questions.size) {
            QuizQuestionDisplay(question = questions[currentQuestionIndex], selectedAnswer, answerSubmitted) { option ->
                if (!answerSubmitted) {
                    selectedAnswer = option
                    answerSubmitted = true
                }
            }

            if (answerSubmitted) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (selectedAnswer == questions[currentQuestionIndex].correctAnswer) {
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
}

@Composable
fun QuizQuestionDisplay(question: Question, selectedAnswer: String?, answerSubmitted: Boolean, onOptionSelected: (String) -> Unit) {
    Column {
        Text(text = question.question, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        question.options.forEach { option ->
            Button(
                onClick = { onOptionSelected(option) },
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
    }
}