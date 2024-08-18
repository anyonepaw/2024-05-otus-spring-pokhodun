package ru.otus.spring.domain

class Question(
    val id: Int,
    val question: String,
    val answers: List<String>,
    val correctAnswers: MutableList<String> = mutableListOf(),
)
