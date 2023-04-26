package ru.otus.spring.domain

class Question (
    val id: Int? = null,
    val question: String,
    val answers: List<String>
)
