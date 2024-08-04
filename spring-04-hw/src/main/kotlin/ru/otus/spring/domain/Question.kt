package ru.otus.spring.domain

class Question(
    val id: Int = IdGenerator.getNextInt(),
    val question: String,
    val answers: List<String>,
    val correctAnswers: MutableList<String> = mutableListOf(),
)

object IdGenerator {
    private var value = 0

    fun getNextInt() = value++
}