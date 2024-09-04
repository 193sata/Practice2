package com.example.practice2.models

data class Question(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)